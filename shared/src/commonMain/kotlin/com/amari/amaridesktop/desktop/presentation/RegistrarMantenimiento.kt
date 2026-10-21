package com.amari.amaridesktop.desktop.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amari.amaridesktop.desktop.presentation.component.Header
import com.amari.amaridesktop.desktop.presentation.ui.gestionmantenimiento.TipoMant
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlin.math.abs

// ==========================================
// 1. UTILIDADES Y CONSTANTES COMUNES
// ==========================================

private const val MILIS_POR_DIA = 86_400_000L

fun formatearMiles(valor: Long): String {
    val s = abs(valor).toString().reversed().chunked(3).joinToString(".").reversed()
    return if (valor < 0) "-$s" else s
}

data class ArchivoAdjunto(
    val nombre: String,
    val ruta: String,
    val tamanoBytes: Long
)

object MantColors {
    val fondo = Color(0xFFF4F6FA)
    val card = Color.White
    val borde = Color(0xFFE3E7EE)
    val texto = Color(0xFF1F2937)
    val textoSec = Color(0xFF6B7280)
    val primario = Color(0xFF2563EB)
    val error = Color(0xFFDC2626)
    val exito = Color(0xFF16A34A)
    val advertencia = Color(0xFFD97706)
}


// ==========================================
// 2. MODELOS - GESTIÓN (PANTALLA 1)
// ==========================================

data class EquipoItem(
    val id: String,
    val uniqueCode: String,
    val nombre: String,
    val categoria: String,
    val ultimoMantenimiento: String,
    val proximoMantenimiento: String,
    val diasRestantes: Int,
    val tipo: TipoMant,
    val estado: String = "Operativo",
    val ubicacion: String = "Edificio Torre Norte",
    val proveedorHabitual: String = "Servicio Técnico Autorizado",
    val periodoMeses: Int = 3
)

data class HistorialITEMMM(
    val fecha: String,
    val titulo: String,
    val proveedor: String,
    val monto: String,
    val tipo: TipoMant,
    val programado: Boolean = false,
    val cotizacionUrl: String? = null,
    val reciboUrl: String? = null,
    val informeUrl: String? = null
)

// ==========================================
// 3. MODELOS - REGISTRO (PANTALLA 2)
// ==========================================

data class EquipoOpcion(
    val uniqueCode: String,
    val nombre: String,
    val periodoMeses: Int?
)

data class NuevoMantenimiento(
    val equipoId: String,
    val tipo: TipoMant,
    val fechaMantenimiento: LocalDate,
    val fechaProximoMantenimiento: LocalDate?,
    val periodoMeses: Int?,
    val proveedor: String,
    val contacto: String,
    val monto: Long,
    val descripcion: String,
    val observaciones: String,
    val cotizacion: ArchivoAdjunto?,
    val recibo: ArchivoAdjunto?,
    val informe: ArchivoAdjunto?
)

// ==========================================
// 4. PANTALLA: REGISTRAR MANTENIMIENTO
// ==========================================

@Composable
fun RegistrarMantenimientoScreen(
    equipos: List<EquipoOpcion>,
    fechaHoy: LocalDate,
    onVolver: () -> Unit,
    onGuardar: (NuevoMantenimiento) -> Unit,
    equipoInicialId: String? = null,
    onElegirArchivo: ((titulo: String, extensiones: List<String>) -> ArchivoAdjunto?)? = null
) {
    var equipo by remember {
        mutableStateOf(equipos.firstOrNull { it.uniqueCode == equipoInicialId }
            ?: equipos.firstOrNull())
    }
    var tipo by remember { mutableStateOf(TipoMant.PREVENTIVO) }
    var fecha by remember { mutableStateOf(fechaHoy) }
    var proximo by remember { mutableStateOf<LocalDate?>(null) }
    var proximoEditado by remember { mutableStateOf(false) }
    var proveedor by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }
    var cotizacion by remember { mutableStateOf<ArchivoAdjunto?>(null) }
    var recibo by remember { mutableStateOf<ArchivoAdjunto?>(null) }
    var informe by remember { mutableStateOf<ArchivoAdjunto?>(null) }
    var intentoGuardar by remember { mutableStateOf(false) }
    var errorArchivo by remember { mutableStateOf<String?>(null) }

    val meses = equipo?.periodoMeses ?: 3

    LaunchedEffect(fecha, equipo) {
        if (!proximoEditado) proximo = fecha.plus(meses, DateTimeUnit.MONTH)
    }

    val errProveedor = intentoGuardar && proveedor.isBlank()
    val errMonto = intentoGuardar && monto.isBlank()
    val errDescripcion = intentoGuardar && descripcion.isBlank()
    val errProximo = proximo?.let { it <= fecha } == true
    val esValido = equipo != null && proveedor.isNotBlank() && monto.isNotBlank() &&
            descripcion.isNotBlank() && !errProximo

    Column(Modifier.fillMaxSize().background(MantColors.fondo)) {
        // Barra superior
        Header(goBack = {
            onVolver.invoke()
        }, "Registrar Mantenimiento")

        // Formulario
        Box(Modifier.weight(1f).fillMaxWidth()) {
            val scroll = rememberScrollState()
            Column(
                Modifier.fillMaxSize().verticalScroll(scroll).padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    Modifier.widthIn(max = 920.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("* Campos obligatorios", fontSize = 12.sp, color = MantColors.textoSec)

                    SeccionForm(
                        1,
                        "Equipo",
                        "Selecciona el equipo intervenido y el tipo de trabajo."
                    ) {
                        EtiquetaCampo("Equipo", true)
                        SelectorEquipoDropdown(equipos, equipo) { equipo = it }
                        Spacer(Modifier.height(14.dp))
                        EtiquetaCampo("Tipo de mantenimiento", true)
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OpcionTipoMant(
                                "Preventivo",
                                "Programado según calendario",
                                tipo == TipoMant.PREVENTIVO,
                                Modifier.weight(1f)
                            ) { tipo = TipoMant.PREVENTIVO }
                            OpcionTipoMant(
                                "Correctivo",
                                "Reparación por falla",
                                tipo == TipoMant.CORRECTIVO,
                                Modifier.weight(1f)
                            ) { tipo = TipoMant.CORRECTIVO }
                        }
                    }

                    SeccionForm(
                        2,
                        "Fechas",
                        "La próxima fecha alimenta el contador de días restantes del listado."
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Column(Modifier.weight(1f)) {
                                EtiquetaCampo("Fecha de realización", true)
                                CampoDatePicker(fecha, onChange = { it?.let { f -> fecha = f } })
                            }
                            Column(Modifier.weight(1f)) {
                                EtiquetaCampo("Próximo mantenimiento programado", false)
                                CampoDatePicker(
                                    proximo,
                                    onChange = { proximo = it; proximoEditado = true },
                                    error = errProximo
                                )
                                Text(
                                    if (errProximo) "Debe ser posterior a la fecha de realización."
                                    else "Sugerido: en $meses meses según historial del equipo.",
                                    fontSize = 12.sp,
                                    color = if (errProximo) MantColors.error else MantColors.textoSec,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }

                    SeccionForm(
                        3,
                        "Proveedor y costo",
                        "Empresa o técnico que realizó el servicio."
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Column(Modifier.weight(1f)) {
                                EtiquetaCampo("Nombre del proveedor", true)
                                CampoTexto(
                                    proveedor,
                                    { proveedor = it },
                                    "Ej. Ascensores Schindler S.A.",
                                    errProveedor
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                EtiquetaCampo("Teléfono / Contacto (opcional)", false)
                                CampoTexto(
                                    contacto,
                                    { contacto = it },
                                    "Ej. 601 345 6789 - Ing. Ramírez"
                                )
                            }
                        }
                        Spacer(Modifier.height(14.dp))
                        Column(Modifier.fillMaxWidth(0.5f)) {
                            EtiquetaCampo("Monto ($)", true)
                            OutlinedTextField(
                                value = monto,
                                onValueChange = { v -> monto = v.filter(Char::isDigit).take(12) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Ej. 1.250.000") },
                                prefix = { Text("$ ") },
                                supportingText = if (monto.isNotBlank()) {
                                    { Text("$ " + formatearMiles(monto.toLong())) }
                                } else null,
                                isError = errMonto,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }

                    SeccionForm(
                        4,
                        "Descripción",
                        "Detalle que aparecerá en el historial anual del equipo."
                    ) {
                        EtiquetaCampo("Descripción del trabajo realizado", true)
                        CampoTexto(
                            descripcion, { descripcion = it },
                            "Ej. Lubricación de guías, ajuste de frenos, revisión de cables de tracción y prueba de paracaídas.",
                            errDescripcion, minLines = 4
                        )
                        Spacer(Modifier.height(14.dp))
                        EtiquetaCampo("Observaciones (opcional)", false)
                        CampoTexto(
                            observaciones,
                            { observaciones = it },
                            "Repuestos pendientes, recomendaciones del técnico, etc.",
                            minLines = 3
                        )
                    }

                    SeccionForm(
                        5,
                        "Documentos adjuntos",
                        "Quedarán disponibles como enlaces en el detalle del equipo."
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            BotonSubirDoc(
                                "Cotización",
                                "PDF, JPG, PNG - máx 5MB",
                                listOf("pdf", "jpg", "jpeg", "png"),
                                5,
                                cotizacion,
                                { cotizacion = it },
                                { errorArchivo = it },
                                onElegirArchivo,
                                Modifier.weight(1f)
                            )
                            BotonSubirDoc(
                                "Recibo / Factura",
                                "PDF, JPG, PNG - máx 5MB",
                                listOf("pdf", "jpg", "jpeg", "png"),
                                5,
                                recibo,
                                { recibo = it },
                                { errorArchivo = it },
                                onElegirArchivo,
                                Modifier.weight(1f)
                            )
                            BotonSubirDoc(
                                "Informe técnico",
                                "PDF, DOC - máx 10MB",
                                listOf("pdf", "doc", "docx"),
                                10,
                                informe,
                                { informe = it },
                                { errorArchivo = it },
                                onElegirArchivo,
                                Modifier.weight(1f)
                            )
                        }
                        errorArchivo?.let {
                            Text(
                                it,
                                color = MantColors.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Footer con acciones
        Surface(color = MantColors.card, border = BorderStroke(1.dp, MantColors.borde)) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    Icons.Default.Info,
                    null,
                    tint = MantColors.textoSec,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    "Se actualizará el historial ${fecha.year} del equipo seleccionado.",
                    fontSize = 13.sp, color = MantColors.textoSec, modifier = Modifier.weight(1f)
                )
                OutlinedButton(
                    onClick = onVolver,
                    shape = RoundedCornerShape(10.dp)
                ) { Text("Cancelar") }
                Button(
                    onClick = {
                        intentoGuardar = true
                        val eq = equipo
                        if (esValido && eq != null) {
                            onGuardar(
                                NuevoMantenimiento(
                                    equipoId = eq.uniqueCode,
                                    tipo = tipo,
                                    fechaMantenimiento = fecha,
                                    fechaProximoMantenimiento = proximo,
                                    periodoMeses = eq.periodoMeses,
                                    proveedor = proveedor.trim(),
                                    contacto = contacto.trim(),
                                    monto = monto.toLongOrNull() ?: 0L,
                                    descripcion = descripcion.trim(),
                                    observaciones = observaciones.trim(),
                                    cotizacion = cotizacion,
                                    recibo = recibo,
                                    informe = informe
                                )
                            )
                        }
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Check, null, Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Guardar Mantenimiento")
                }
            }
        }
    }
}

// ==========================================
// 5. PANTALLA: GESTIÓN DE MANTENIMIENTO
// ==========================================

@Composable
fun GestionMantenimientoScreen(
    equipos: List<EquipoItem>,
    aniosDisponibles: List<Int>,
    onAgregar: () -> Unit,
    onDescargarReporte: () -> Unit = {}
) {
    var equipoSeleccionado by remember { mutableStateOf(equipos.firstOrNull()) }
    var anioSeleccionado by remember { mutableStateOf(aniosDisponibles.firstOrNull() ?: 2026) }
    var busqueda by remember { mutableStateOf("") }
    var filtroTipo by remember { mutableStateOf("Todos") }

    val equiposFiltrados = remember(equipos, busqueda, filtroTipo) {
        equipos.filter { eq ->
            val coincideTexto = eq.nombre.contains(busqueda, ignoreCase = true) ||
                    eq.categoria.contains(busqueda, ignoreCase = true)
            val coincideTipo = when (filtroTipo) {
                "Preventivo" -> eq.tipo == TipoMant.PREVENTIVO
                "Correctivo" -> eq.tipo == TipoMant.CORRECTIVO
                else -> true
            }
            coincideTexto && coincideTipo
        }
    }

    Column(Modifier.fillMaxSize().background(MantColors.fondo)) {
        // Cabecera principal
        Surface(color = MantColors.card, border = BorderStroke(1.dp, MantColors.borde)) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        "Gestión de Mantenimiento",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MantColors.texto
                    )
                    Text(
                        "Control y seguimiento de equipos e infraestructura del edificio",
                        fontSize = 13.sp,
                        color = MantColors.textoSec
                    )
                }
                OutlinedButton(onClick = onDescargarReporte, shape = RoundedCornerShape(10.dp)) {
                    Icon(Icons.Default.Share, null, Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Reporte Anual")
                }
                Spacer(Modifier.width(12.dp))
                Button(onClick = onAgregar, shape = RoundedCornerShape(10.dp)) {
                    Icon(Icons.Default.Add, null, Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Registrar Mantenimiento")
                }
            }
        }

        // Split view
        Row(
            Modifier.weight(1f).fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Panel Izquierdo: Lista de equipos
            Surface(
                modifier = Modifier.weight(0.42f).fillMaxHeight(),
                shape = RoundedCornerShape(16.dp),
                color = MantColors.card,
                border = BorderStroke(1.dp, MantColors.borde)
            ) {
                Column(Modifier.fillMaxSize().padding(16.dp)) {
                    OutlinedTextField(
                        value = busqueda,
                        onValueChange = { busqueda = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Buscar equipo...") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                null,
                                tint = MantColors.textoSec
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Todos", "Preventivo", "Correctivo").forEach { chip ->
                            FilterChip(
                                selected = filtroTipo == chip,
                                onClick = { filtroTipo = chip },
                                label = { Text(chip, fontSize = 12.sp) }
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    val listState = rememberLazyListState()
                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(equiposFiltrados, key = { it.id }) { eq ->
                            ItemEquipoLista(
                                equipo = eq,
                                seleccionado = eq.id == equipoSeleccionado?.id,
                                onSelect = { equipoSeleccionado = eq }
                            )
                        }
                    }
                }
            }

            // Panel Derecho: Detalle e Historial
            Surface(
                modifier = Modifier.weight(0.58f).fillMaxHeight(),
                shape = RoundedCornerShape(16.dp),
                color = MantColors.card,
                border = BorderStroke(1.dp, MantColors.borde)
            ) {
                equipoSeleccionado?.let { eq ->
                    val historialScroll = rememberLazyListState()
                    LazyColumn(
                        state = historialScroll,
                        modifier = Modifier.fillMaxSize().padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            // Cabecera del equipo
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    Modifier.size(48.dp).background(
                                        MantColors.primario.copy(alpha = 0.1f),
                                        RoundedCornerShape(12.dp)
                                    ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Build, null, tint = MantColors.primario)
                                }
                                Spacer(Modifier.width(14.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        eq.nombre,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MantColors.texto
                                    )
                                    Text(
                                        "${eq.categoria} • ${eq.ubicacion}",
                                        fontSize = 13.sp,
                                        color = MantColors.textoSec
                                    )
                                }
                                SelectorAnio(
                                    aniosDisponibles,
                                    anioSeleccionado
                                ) { anioSeleccionado = it }
                            }
                        }

                        item {
                            // KPIs
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                KpiCard(
                                    "Próximo en",
                                    "${eq.diasRestantes} días",
                                    eq.proximoMantenimiento,
                                    Modifier.weight(1f)
                                )
                                KpiCard(
                                    "Último servicio",
                                    eq.ultimoMantenimiento,
                                    eq.proveedorHabitual,
                                    Modifier.weight(1f)
                                )
                                KpiCard(
                                    "Estado",
                                    eq.estado,
                                    "Frecuencia: ${eq.periodoMeses}m",
                                    Modifier.weight(1f)
                                )
                            }
                        }

                        item {
                            Text(
                                "Historial de Mantenimientos ($anioSeleccionado)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MantColors.texto
                            )
                        }

                        // Muestra de historial simulado para el preview
                        items(
                            listOf(
                                HistorialITEMMM(
                                    "2026-10-13",
                                    "Mantenimiento Preventivo Trimestral",
                                    "Ascensores Schindler S.A.",
                                    "$ 1.250.000",
                                    TipoMant.PREVENTIVO
                                ),
                                HistorialITEMMM(
                                    "2026-07-10",
                                    "Revisión cables y motor",
                                    "Ascensores Schindler S.A.",
                                    "$ 850.000",
                                    TipoMant.PREVENTIVO
                                ),
                                HistorialITEMMM(
                                    "2026-04-02",
                                    "Cambio de botonera piso 4",
                                    "Ascensores Schindler S.A.",
                                    "$ 320.000",
                                    TipoMant.CORRECTIVO
                                )
                            )
                        ) { h ->
                            FilaHistorial(h)
                        }
                    }
                } ?: run {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "Selecciona un equipo para ver su detalle",
                            color = MantColors.textoSec
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 6. COMPONENTES REUTILIZABLES AUXILIARES
// ==========================================

@Composable
private fun SeccionForm(
    numero: Int,
    titulo: String,
    descripcion: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MantColors.card,
        border = BorderStroke(1.dp, MantColors.borde)
    ) {
        Column(Modifier.fillMaxWidth().padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(28.dp).background(MantColors.primario, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "$numero",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        titulo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MantColors.texto
                    )
                    Text(descripcion, fontSize = 12.sp, color = MantColors.textoSec)
                }
            }
            Spacer(Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
private fun EtiquetaCampo(texto: String, obligatorio: Boolean) {
    Row(Modifier.padding(bottom = 6.dp)) {
        Text(texto, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MantColors.texto)
        if (obligatorio) Text(" *", fontSize = 13.sp, color = MantColors.error)
    }
}

@Composable
private fun CampoTexto(
    valor: String,
    onChange: (String) -> Unit,
    placeholder: String,
    error: Boolean = false,
    minLines: Int = 1
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder, color = MantColors.textoSec) },
        isError = error,
        supportingText = if (error) {
            { Text("Campo obligatorio") }
        } else null,
        singleLine = minLines == 1,
        minLines = minLines,
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
private fun SelectorEquipoDropdown(
    equipos: List<EquipoOpcion>,
    seleccionado: EquipoOpcion?,
    onSelect: (EquipoOpcion) -> Unit
) {
    var abierto by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = seleccionado?.nombre ?: "",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Selecciona un equipo") },
            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
            shape = RoundedCornerShape(10.dp)
        )
        Box(Modifier.matchParentSize().clip(RoundedCornerShape(10.dp)).clickable { abierto = true })
        DropdownMenu(expanded = abierto, onDismissRequest = { abierto = false }) {
            equipos.forEach {
                DropdownMenuItem(
                    text = { Text(it.nombre) },
                    onClick = { onSelect(it); abierto = false })
            }
        }
    }
}

@Composable
private fun OpcionTipoMant(
    titulo: String,
    subtitulo: String,
    seleccionado: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = if (seleccionado) MantColors.primario.copy(alpha = 0.06f) else MantColors.card,
        border = BorderStroke(
            if (seleccionado) 2.dp else 1.dp,
            if (seleccionado) MantColors.primario else MantColors.borde
        )
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = seleccionado, onClick = onClick)
            Column {
                Text(titulo, fontWeight = FontWeight.SemiBold, color = MantColors.texto)
                Text(subtitulo, fontSize = 12.sp, color = MantColors.textoSec)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CampoDatePicker(
    fecha: LocalDate?,
    onChange: (LocalDate?) -> Unit,
    error: Boolean = false
) {
    var abierto by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = fecha?.toString() ?: "",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("aaaa-mm-dd") },
            trailingIcon = { Icon(Icons.Default.DateRange, null) },
            isError = error,
            shape = RoundedCornerShape(10.dp)
        )
        Box(Modifier.matchParentSize().clip(RoundedCornerShape(10.dp)).clickable { abierto = true })
    }

    if (abierto) {
        val state = rememberDatePickerState(
            initialSelectedDateMillis = fecha?.let { it.toEpochDays().toLong() * MILIS_POR_DIA }
        )
        DatePickerDialog(
            onDismissRequest = { abierto = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let { onChange(LocalDate.fromEpochDays((it / MILIS_POR_DIA).toInt())) }
                    abierto = false
                }) { Text("Aceptar") }
            },
            dismissButton = { TextButton(onClick = { abierto = false }) { Text("Cancelar") } }
        ) {
            DatePicker(state = state)
        }
    }
}

@Composable
private fun BotonSubirDoc(
    titulo: String, formatos: String, extensiones: List<String>, maxMB: Int,
    archivo: ArchivoAdjunto?, onArchivo: (ArchivoAdjunto?) -> Unit, onError: (String?) -> Unit,
    onElegirArchivo: ((String, List<String>) -> ArchivoAdjunto?)?, modifier: Modifier
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).clickable {
            val f = onElegirArchivo?.invoke(titulo, extensiones) ?: run {
                // Mock si no se suministra el selector nativo
                ArchivoAdjunto(
                    "documento_adjunto.pdf",
                    "/temp/documento_adjunto.pdf",
                    1024L * 1024L
                )
            }
            when {
                extensiones.none {
                    f.nombre.lowercase().endsWith(".$it")
                } -> onError("$titulo: formato no permitido ($formatos).")

                f.tamanoBytes > maxMB * 1024L * 1024L -> onError("$titulo: el archivo supera $maxMB MB.")
                else -> {
                    onError(null); onArchivo(f)
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        color = if (archivo != null) MantColors.primario.copy(alpha = 0.05f) else MantColors.fondo,
        border = BorderStroke(1.dp, if (archivo != null) MantColors.primario else MantColors.borde)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(titulo, fontWeight = FontWeight.SemiBold, color = MantColors.texto)
            Icon(
                if (archivo != null) Icons.Default.CheckCircle else Icons.Default.Add,
                null, tint = MantColors.primario, modifier = Modifier.size(28.dp)
            )
            if (archivo != null) {
                Text(
                    archivo.nombre,
                    fontSize = 12.sp,
                    color = MantColors.texto,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                TextButton(onClick = { onArchivo(null) }) {
                    Text(
                        "Quitar",
                        fontSize = 12.sp,
                        color = MantColors.error
                    )
                }
            } else {
                Text("Arrastra o haz clic para subir", fontSize = 12.sp, color = MantColors.texto)
                Text(formatos, fontSize = 11.sp, color = MantColors.textoSec)
            }
        }
    }
}

@Composable
private fun ItemEquipoLista(equipo: EquipoItem, seleccionado: Boolean, onSelect: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(12.dp),
        color = if (seleccionado) MantColors.primario.copy(alpha = 0.08f) else MantColors.card,
        border = BorderStroke(
            if (seleccionado) 1.5.dp else 1.dp,
            if (seleccionado) MantColors.primario else MantColors.borde
        )
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(40.dp).background(MantColors.fondo, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Build,
                    null,
                    tint = MantColors.textoSec,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(equipo.nombre, fontWeight = FontWeight.SemiBold, color = MantColors.texto)
                Text(equipo.categoria, fontSize = 12.sp, color = MantColors.textoSec)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${equipo.diasRestantes}d",
                    fontWeight = FontWeight.Bold,
                    color = if (equipo.diasRestantes < 15) MantColors.error else MantColors.exito
                )
                Text(
                    equipo.tipo.name.lowercase().replaceFirstChar { it.uppercase() },
                    fontSize = 11.sp,
                    color = MantColors.textoSec
                )
            }
        }
    }
}

@Composable
private fun KpiCard(etiqueta: String, valor: String, detalle: String, modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MantColors.fondo,
        border = BorderStroke(1.dp, MantColors.borde)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(etiqueta, fontSize = 12.sp, color = MantColors.textoSec)
            Text(valor, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MantColors.texto)
            Text(
                detalle,
                fontSize = 11.sp,
                color = MantColors.textoSec,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun FilaHistorial(item: HistorialITEMMM) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MantColors.fondo,
        border = BorderStroke(1.dp, MantColors.borde)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(item.titulo, fontWeight = FontWeight.SemiBold, color = MantColors.texto)
                Text(
                    "${item.fecha} • ${item.proveedor}",
                    fontSize = 12.sp,
                    color = MantColors.textoSec
                )
            }
            Text(item.monto, fontWeight = FontWeight.Bold, color = MantColors.texto)
        }
    }
}

@Composable
private fun SelectorAnio(anios: List<Int>, anioSeleccionado: Int, onSelect: (Int) -> Unit) {
    var abierto by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { abierto = true }, shape = RoundedCornerShape(8.dp)) {
            Text("$anioSeleccionado")
            Icon(Icons.Default.ArrowDropDown, null)
        }
        DropdownMenu(expanded = abierto, onDismissRequest = { abierto = false }) {
            anios.forEach { a ->
                DropdownMenuItem(text = { Text("$a") }, onClick = { onSelect(a); abierto = false })
            }
        }
    }
}


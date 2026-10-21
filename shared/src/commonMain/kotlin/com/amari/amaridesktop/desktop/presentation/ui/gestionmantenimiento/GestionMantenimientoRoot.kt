package com.amari.amaridesktop.desktop.presentation.ui.gestionmantenimiento

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amari.amaridesktop.desktop.navigator.GestionMantenimiento
import com.amari.amaridesktop.desktop.presentation.component.Header
import com.amari.amaridesktop.desktop.presentation.ui.gestionmantenimiento.event.GestionMantenimientoEvent
import com.amari.amaridesktop.desktop.presentation.ui.newprovider.event.NewProviderEvent
import org.koin.compose.koinInject

// ---------- Colores ----------
private object MColors {
    val fondo = Color(0xFFF4F6FA)
    val card = Color.White
    val borde = Color(0xFFE3E7EE)
    val texto = Color(0xFF1F2937)
    val textoSec = Color(0xFF6B7280)
    val primario = Color(0xFF2563EB)
    val urgente = Color(0xFFDC2626)
    val proximo = Color(0xFFF59E0B)
    val alDia = Color(0xFF16A34A)
    val preventivo = Color(0xFF2563EB)
    val correctivo = Color(0xFF9333EA)
}

// ---------- Modelos ----------
enum class TipoMant(val label: String) { PREVENTIVO("PREVENTIVO"), CORRECTIVO("CORRECTIVO") }

data class HistorialItem(
    val fecha: String,
    val tipo: TipoMant,
    val descripcion: String,
    val proveedor: String,
    val monto: String,
    val programado: Boolean = false
)

data class Equipo(
    val id: String,
    val nombre: String,
    val tipo: TipoMant,
    val ultimo: String,
    val diasRestantes: Int,
    val codigo: String,
    val ubicacion: String,
    val instalado: String,
    val descripcion: String,
    val proximaFecha: String,
    val proveedor: String,
    val contacto: String,
    val telefono: String,
    val montoProximo: String,
    val cotizacion: String,
    val imagenUrl: String? = null,
    val historial: List<HistorialItem> = emptyList()
)

private enum class Estado(val label: String, val color: Color) {
    URGENTE("Urgente", MColors.urgente),
    PROXIMO("Próximo", MColors.proximo),
    AL_DIA("Al día", MColors.alDia)
}

private fun estadoDe(dias: Int) = when {
    dias <= 7 -> Estado.URGENTE
    dias <= 30 -> Estado.PROXIMO
    else -> Estado.AL_DIA
}

@Composable
fun GestionMantenimientoRoot() {
    val vm: GestionMantenimientoViewmodel = koinInject()
    GestionMantenimientoScreen(
        onEvent = vm::onEvent,
        equipos = equiposDemo,
        aniosDisponibles = listOf(2026, 2025, 2024)
    )
}

// ---------- Pantalla ----------
@Composable
fun GestionMantenimientoScreen(
    onEvent: (GestionMantenimientoEvent) -> Unit,
    equipos: List<Equipo>,
    aniosDisponibles: List<Int>,
    anioInicial: Int = aniosDisponibles.firstOrNull() ?: 2025,
    onAnioChange: (Int) -> Unit = {},
    onAgregar: () -> Unit = {},
    onProgramar: (Equipo) -> Unit = {}
) {
    var anio by remember { mutableStateOf(anioInicial) }
    var filtro by remember { mutableStateOf<TipoMant?>(null) }
    var busqueda by remember { mutableStateOf("") }
    var seleccionadoId by remember { mutableStateOf(equipos.firstOrNull()?.id) }

    val filtrados = remember(equipos, filtro, busqueda) {
        equipos
            .filter { filtro == null || it.tipo == filtro }
            .filter {
                busqueda.isBlank() ||
                        it.nombre.contains(busqueda, true) ||
                        it.proveedor.contains(busqueda, true) ||
                        it.ubicacion.contains(busqueda, true)
            }
            .sortedBy { it.diasRestantes }
    }
    val seleccionado = equipos.firstOrNull { it.id == seleccionadoId }

    Column(
        Modifier.fillMaxSize().background(MColors.fondo).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Header(goBack = {
            onEvent.invoke(GestionMantenimientoEvent.GoBack())
        }, "Gestion de mantenimiento")

        Header(
            equipos = equipos,
            anio = anio,
            anios = aniosDisponibles,
            onAnioChange = { anio = it; onAnioChange(it) }
        )

        Row(
            Modifier.weight(1f).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PanelEquipos(
                modifier = Modifier.weight(0.38f).fillMaxHeight(),
                equipos = filtrados,
                total = equipos.size,
                preventivos = equipos.count { it.tipo == TipoMant.PREVENTIVO },
                correctivos = equipos.count { it.tipo == TipoMant.CORRECTIVO },
                filtro = filtro,
                onFiltro = { filtro = it },
                busqueda = busqueda,
                onBusqueda = { busqueda = it },
                seleccionadoId = seleccionadoId,
                onSeleccionar = { seleccionadoId = it.id },
                onAgregar = onAgregar
            )

            Box(Modifier.weight(0.62f).fillMaxHeight()) {
                if (seleccionado != null) {
                    PanelDetalle(seleccionado, anio, onProgramar)
                } else {
                    Text(
                        "Selecciona un equipo",
                        Modifier.align(Alignment.Center),
                        color = MColors.textoSec
                    )
                }
            }
        }
    }
}

// ---------- Header ----------
@Composable
private fun Header(
    equipos: List<Equipo>,
    anio: Int,
    anios: List<Int>,
    onAnioChange: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Kpi("Total equipos", equipos.size.toString(), MColors.texto)
            Kpi(
                "Pendientes (≤ 30 días)",
                equipos.count { it.diasRestantes <= 30 }.toString(),
                MColors.proximo
            )
            Kpi("Urgentes", equipos.count { it.diasRestantes <= 7 }.toString(), MColors.urgente)
            Spacer(Modifier.weight(1f))
            SelectorAnio(anio, anios, onAnioChange)
        }
    }
}

@Composable
private fun Kpi(label: String, valor: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MColors.card,
        border = BorderStroke(1.dp, MColors.borde)
    ) {
        Column(Modifier.padding(horizontal = 18.dp, vertical = 10.dp)) {
            Text(label, fontSize = 12.sp, color = MColors.textoSec)
            Text(valor, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
private fun SelectorAnio(anio: Int, anios: List<Int>, onChange: (Int) -> Unit) {
    var abierto by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { abierto = true }, shape = RoundedCornerShape(10.dp)) {
            Text("AÑO $anio", fontWeight = FontWeight.SemiBold)
            Icon(Icons.Default.ArrowDropDown, null)
        }
        DropdownMenu(expanded = abierto, onDismissRequest = { abierto = false }) {
            anios.forEach {
                DropdownMenuItem(
                    text = { Text(it.toString()) },
                    onClick = { onChange(it); abierto = false })
            }
        }
    }
}

// ---------- Panel izquierdo: Equipos (scroll) ----------
@Composable
private fun PanelEquipos(
    modifier: Modifier,
    equipos: List<Equipo>,
    total: Int,
    preventivos: Int,
    correctivos: Int,
    filtro: TipoMant?,
    onFiltro: (TipoMant?) -> Unit,
    busqueda: String,
    onBusqueda: (String) -> Unit,
    seleccionadoId: String?,
    onSeleccionar: (Equipo) -> Unit,
    onAgregar: () -> Unit
) {
    val listState = rememberLazyListState()

    Surface(
        modifier,
        shape = RoundedCornerShape(16.dp),
        color = MColors.card,
        border = BorderStroke(1.dp, MColors.borde)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(
                        "Equipos",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MColors.texto
                    )
                    Text(
                        "Ordenados por días restantes al próximo mantenimiento",
                        fontSize = 12.sp,
                        color = MColors.textoSec
                    )
                }
                Button(onClick = onAgregar, shape = RoundedCornerShape(10.dp)) {
                    Icon(
                        Icons.Default.Add,
                        null,
                        Modifier.size(18.dp)
                    ); Spacer(Modifier.width(4.dp)); Text("Agregar")
                }
            }

            OutlinedTextField(
                value = busqueda,
                onValueChange = onBusqueda,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar equipo, proveedor o ubicación.") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(filtro == null, { onFiltro(null) }, label = { Text("Todos $total") })
                FilterChip(
                    filtro == TipoMant.PREVENTIVO,
                    { onFiltro(TipoMant.PREVENTIVO) },
                    label = { Text("Preventivo $preventivos") })
                FilterChip(
                    filtro == TipoMant.CORRECTIVO,
                    { onFiltro(TipoMant.CORRECTIVO) },
                    label = { Text("Correctivo $correctivos") })
            }

            // Lista con scroll propio
            Box(Modifier.weight(1f).fillMaxWidth()) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize().padding(end = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(equipos, key = { it.id }) {
                        CardEquipo(it, it.id == seleccionadoId) { onSeleccionar(it) }
                    }
                }
                VerticalScrollbar(
                    adapter = rememberScrollbarAdapter(listState),
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    style = estiloScrollbar()
                )
            }

            Leyenda()
        }
    }
}

@Composable
private fun CardEquipo(equipo: Equipo, seleccionado: Boolean, onClick: () -> Unit) {
    val estado = estadoDe(equipo.diasRestantes)
    Surface(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = if (seleccionado) MColors.primario.copy(alpha = 0.06f) else MColors.card,
        border = BorderStroke(
            if (seleccionado) 2.dp else 1.dp,
            if (seleccionado) MColors.primario else MColors.borde
        )
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            ImagenEquipo(equipo, 52.dp)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    equipo.nombre,
                    fontWeight = FontWeight.SemiBold,
                    color = MColors.texto,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                ChipTipo(equipo.tipo)
                Text("Último: ${equipo.ultimo}", fontSize = 12.sp, color = MColors.textoSec)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${equipo.diasRestantes} días",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = estado.color
                )
                Badge(estado.label, estado.color)
            }
        }
    }
}

@Composable
private fun Leyenda() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            "LEYENDA:",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = MColors.textoSec
        )
        Punto(MColors.urgente, "Urgente - menos de 7 días")
        Punto(MColors.proximo, "8-30 días")
        Punto(MColors.alDia, "Más de 30")
    }
}

@Composable
private fun Punto(color: Color, texto: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(8.dp).background(color, CircleShape))
        Spacer(Modifier.width(4.dp))
        Text(texto, fontSize = 11.sp, color = MColors.textoSec)
    }
}

// ---------- Panel derecho: Detalle ----------
@Composable
private fun PanelDetalle(equipo: Equipo, anio: Int, onProgramar: (Equipo) -> Unit) {
    val estado = estadoDe(equipo.diasRestantes)

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // Cabecera del equipo
        Tarjeta {
            Row(verticalAlignment = Alignment.Top) {
                ImagenEquipo(equipo, 72.dp)
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            equipo.nombre,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MColors.texto
                        )
                        ChipTipo(equipo.tipo)
                    }
                    Text(
                        "Código ${equipo.codigo} · ${equipo.ubicacion} · Instalado en ${equipo.instalado}",
                        fontSize = 12.sp,
                        color = MColors.textoSec
                    )
                    Text(
                        equipo.descripcion,
                        fontSize = 13.sp,
                        color = MColors.texto,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Button(onClick = { onProgramar(equipo) }, shape = RoundedCornerShape(10.dp)) {
                    Icon(
                        Icons.Default.DateRange,
                        null,
                        Modifier.size(18.dp)
                    ); Spacer(Modifier.width(6.dp))
                    Text("Programar mantenimiento")
                }
            }
            Spacer(Modifier.height(12.dp))
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = estado.color.copy(alpha = 0.1f),
                border = BorderStroke(1.dp, estado.color.copy(alpha = 0.4f))
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Warning, null, tint = estado.color)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Mantenimiento ${equipo.tipo.label.lowercase()} en ${equipo.diasRestantes} días — programado para el " +
                                "${equipo.proximaFecha} con ${equipo.proveedor}. Confirma la visita con el proveedor.",
                        fontSize = 13.sp, color = MColors.texto
                    )
                }
            }
        }

        // Proveedor y monto
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Tarjeta(Modifier.weight(1f)) {
                Etiqueta("PROVEEDOR ACTUAL")
                Text(equipo.proveedor, fontWeight = FontWeight.SemiBold, color = MColors.texto)
                Text(
                    "${equipo.contacto} · ${equipo.telefono}",
                    fontSize = 12.sp,
                    color = MColors.textoSec
                )
            }
            Tarjeta(Modifier.weight(1f)) {
                Etiqueta("MONTO PRÓXIMO MANTENIMIENTO")
                Text(
                    equipo.montoProximo,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MColors.texto
                )
                Text(
                    "Según cotización ${equipo.cotizacion}",
                    fontSize = 12.sp,
                    color = MColors.textoSec
                )
            }
        }

        // Historial con scroll propio
        Tarjeta(Modifier.weight(1f).fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(
                        "Historial de $anio",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MColors.texto
                    )
                    Text(
                        "Mantenimientos realizados y programados para este equipo",
                        fontSize = 12.sp,
                        color = MColors.textoSec
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Etiqueta("GASTO EN EL AÑO")
                    Text(
                        gastoAnual(equipo.historial),
                        fontWeight = FontWeight.Bold,
                        color = MColors.texto
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            HistorialTabla(equipo.historial, Modifier.weight(1f))
        }

        // Documentos
        Tarjeta(Modifier.fillMaxWidth()) {
            Text(
                "Documentos del último mantenimiento",
                fontWeight = FontWeight.SemiBold,
                color = MColors.texto
            )
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Documento("Cotización · PDF")
                Documento("Recibo · PDF")
                Documento("Informe · PDF")
            }
        }
    }
}

@Composable
private fun HistorialTabla(items: List<HistorialItem>, modifier: Modifier) {
    val listState = rememberLazyListState()
    Column(modifier) {
        // Encabezado fijo
        Row(
            Modifier.fillMaxWidth().background(MColors.fondo, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Celda("FECHA", 0.15f, true); Celda("TIPO", 0.17f, true); Celda(
            "DESCRIPCIÓN",
            0.33f,
            true
        )
            Celda("PROVEEDOR", 0.2f, true); Celda("MONTO", 0.15f, true)
        }
        Box(Modifier.weight(1f).fillMaxWidth()) {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize().padding(end = 14.dp)) {
                items(items) { h ->
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Celda(h.fecha, 0.15f)
                        Box(Modifier.weight(0.17f)) {
                            Column {
                                if (h.programado) Text(
                                    "Programado",
                                    fontSize = 10.sp,
                                    color = MColors.proximo
                                )
                                ChipTipo(h.tipo)
                            }
                        }
                        Celda(h.descripcion, 0.33f)
                        Celda(h.proveedor, 0.2f)
                        Celda(h.monto, 0.15f)
                    }
                    HorizontalDivider(color = MColors.borde)
                }
            }
            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(listState),
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                style = estiloScrollbar()
            )
        }
    }
}

// ---------- Componentes pequeños ----------
@Composable
private fun RowScope.Celda(texto: String, peso: Float, header: Boolean = false) {
    Text(
        texto,
        modifier = Modifier.weight(peso),
        fontSize = if (header) 11.sp else 13.sp,
        fontWeight = if (header) FontWeight.SemiBold else FontWeight.Normal,
        color = if (header) MColors.textoSec else MColors.texto,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun Tarjeta(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier,
        shape = RoundedCornerShape(16.dp),
        color = MColors.card,
        border = BorderStroke(1.dp, MColors.borde)
    ) {
        Column(Modifier.padding(16.dp), content = content)
    }
}

@Composable
private fun ImagenEquipo(equipo: Equipo, size: Dp) {
    // Reemplaza por tu carga de imagen (p. ej. Coil 3 AsyncImage con equipo.imagenUrl)
    Box(
        Modifier.size(size).clip(RoundedCornerShape(10.dp))
            .background(MColors.primario.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.Build, contentDescription = equipo.nombre, tint = MColors.primario)
    }
}

@Composable
private fun ChipTipo(tipo: TipoMant) {
    val color = if (tipo == TipoMant.PREVENTIVO) MColors.preventivo else MColors.correctivo
    Text(
        tipo.label,
        modifier = Modifier.background(color.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = color
    )
}

@Composable
private fun Badge(texto: String, color: Color) {
    Text(
        texto,
        modifier = Modifier.background(color.copy(alpha = 0.12f), RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = color
    )
}

@Composable
private fun Etiqueta(texto: String) =
    Text(texto, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MColors.textoSec)

@Composable
private fun Documento(nombre: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MColors.borde),
        modifier = Modifier.clickable { }) {
        Row(
            Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Info, null, tint = MColors.urgente, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(6.dp))
            Text(nombre, fontSize = 12.sp, color = MColors.texto)
        }
    }
}

@Composable
private fun estiloScrollbar() = ScrollbarStyle(
    minimalHeight = 48.dp,
    thickness = 10.dp,
    shape = RoundedCornerShape(5.dp),
    hoverDurationMillis = 300,
    unhoverColor = MColors.primario.copy(alpha = 0.35f),
    hoverColor = MColors.primario
)

private fun gastoAnual(items: List<HistorialItem>): String {
    val total = items.filterNot { it.programado }
        .sumOf { it.monto.filter(Char::isDigit).toLongOrNull() ?: 0L }
    return "$ " + "%,d"
}


val equiposDemo = listOf(
    Equipo(
        id = "1",
        nombre = "Ascensor Principal",
        tipo = TipoMant.PREVENTIVO,
        ultimo = "15/01/2025",
        diasRestantes = 3,
        codigo = "ASC-001",
        ubicacion = "Torre Norte",
        instalado = "2018",
        descripcion = "Descripción del equipo (capacidad y requisitos de mantenimiento).",
        proximaFecha = "15/04/2025",
        proveedor = "Elevadores Andinos",
        contacto = "Contacto",
        telefono = "Teléfono",
        montoProximo = "$ 1.250.000",
        cotizacion = "N.º cotización",
        historial = listOf(
            HistorialItem(
                "15/04/2025",
                TipoMant.PREVENTIVO,
                "Mantenimiento programado",
                "Elevadores Andinos",
                "$ 1.250.000",
                programado = true
            ),
            HistorialItem(
                "15/01/2025",
                TipoMant.PREVENTIVO,
                "Mantenimiento preventivo",
                "Elevadores Andinos",
                "$ 1.180.000"
            ),
            HistorialItem(
                "09/01/2025",
                TipoMant.CORRECTIVO,
                "Mantenimiento correctivo",
                "Elevadores Andinos",
                "$ 1.230.000"
            ),
            HistorialItem(
                "03/01/2025",
                TipoMant.CORRECTIVO,
                "Mantenimiento correctivo",
                "TecnoLift Ltda.",
                "$ 720.000"
            ),
        )
    ),
    Equipo(
        "2",
        "Sistema HVAC",
        TipoMant.PREVENTIVO,
        "20/03/2025",
        22,
        "HVAC-001",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-"
    ),
    Equipo(
        "3",
        "Bomba de Agua",
        TipoMant.PREVENTIVO,
        "05/02/2025",
        12,
        "BOM-001",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-"
    ),
    Equipo(
        "4",
        "Planta Eléctrica",
        TipoMant.PREVENTIVO,
        "10/04/2025",
        58,
        "PEL-001",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-"
    ),
    Equipo(
        "5",
        "Sistema Contra Incendios",
        TipoMant.CORRECTIVO,
        "01/05/2025",
        90,
        "SCI-001",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-",
        "-"
    ),
)

// Uso:


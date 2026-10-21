package com.amari.amaridesktop.desktop.presentation.ui.pantallainicial
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amari.amaridesktop.desktop.presentation.ui.pantallainicial.components.InicioHeader
import com.amari.amaridesktop.desktop.presentation.ui.pantallainicial.event.InicioEvent
import com.amari.amaridesktop.desktop.theme.Colors
import org.koin.compose.koinInject

// ==========================================
// COLORES
// ==========================================

// ==========================================
// MODELO
// ==========================================

data class InicioResumen(
    val edificio: String = "Edificio Torre Norte",
    val usuarioNombre: String = "Admin",
    val usuarioRol: String = "Administrador",
    val fechaTexto: String = "Jueves, 15 de octubre",  // ya formateada desde la plataforma
    val horaActual: Int = 9,                           // 0..23, para el saludo
    val totalEquipos: Int = 5,
    val equiposUrgentes: Int = 1,
    val ultimoRegistroTexto: String = "hace 3 días",
    val totalProveedores: Int = 12,
    val notificacionesPendientes: Int = 0,
    val version: String = "v1.0",
    val anio: Int = 2025
)

// ==========================================
// PANTALLA
// ==========================================


@Composable
fun InicioScreenRoot(){

    val vm : InicioViewmodel = koinInject()


    InicioScreen(
        resumen = InicioResumen(
            edificio = "Edificio Amari",
            usuarioNombre = "Admin",
            usuarioRol = "Administrador",
            fechaTexto = "Jueves, 15 de octubre",
            horaActual = 9,
            totalEquipos = 5,
            equiposUrgentes = 1,
            ultimoRegistroTexto = "hace 3 días",
            totalProveedores = 12,
            notificacionesPendientes = 2,
            version = "v1.0",
            anio = 2026
        ),
        onGestionMantenimiento = {
           vm.onEvent(InicioEvent.GestionMantenimiento())
        },
        onRegistrarMantenimiento = {
            vm.onEvent(InicioEvent.RegistrarMantenimiento())
        },
        onNuevoProveedor = {
            vm.onEvent(InicioEvent.NuevoProveedor())
        },
        onNotificaciones = {
            println("Abrir notificaciones")
        },
        onPerfil = {
            println("Abrir perfil")
        }
    )

}

@Composable
fun InicioScreen(
    resumen: InicioResumen,
    onGestionMantenimiento: () -> Unit,
    onRegistrarMantenimiento: () -> Unit,
    onNuevoProveedor: () -> Unit,
    onNotificaciones: () -> Unit = {},
    onPerfil: () -> Unit = {}
) {
    Column(Modifier.fillMaxSize().background(Colors.fondo)) {

        InicioHeader(resumen, onNotificaciones, onPerfil)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.widthIn(max = 760.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Saludo
                Column(Modifier.padding(bottom = 8.dp)) {
                    Text(resumen.fechaTexto, fontSize = 13.sp, color = Colors.textoSec)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "${saludo(resumen.horaActual)}, ${resumen.usuarioNombre}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Colors.texto
                    )
                    Text("¿Qué deseas hacer hoy?", fontSize = 15.sp, color = Colors.textoSec)
                }
                Row(
                    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    TarjetaAccion(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        icono = Icons.Default.Build,
                        color = Colors.azul,
                        titulo = "Gestión de Mantenimiento",
                        subtitulo = "Revisa el estado de todos los equipos del edificio y su historial",
                        onClick = onGestionMantenimiento
                    ) {
                        Text(
                            buildAnnotatedString {
                                append("${resumen.totalEquipos} equipos")
                                if (resumen.equiposUrgentes > 0) {
                                    append(" · ")
                                    withStyle(SpanStyle(color = Colors.urgente, fontWeight = FontWeight.SemiBold)) {
                                        append("${resumen.equiposUrgentes} urgente" + if (resumen.equiposUrgentes > 1) "s" else "")
                                    }
                                }
                            },
                            fontSize = 12.sp,
                            color = Colors.textoSec
                        )
                    }

                    TarjetaAccion(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        icono = Icons.Default.Add,
                        color = Colors.verde,
                        titulo = "Registrar Mantenimiento",
                        subtitulo = "Ingresa un nuevo mantenimiento realizado o programado",
                        onClick = onRegistrarMantenimiento
                    ) {
                        Text("Último: ${resumen.ultimoRegistroTexto}", fontSize = 12.sp, color = Colors.textoSec)
                    }

                    TarjetaAccion(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        icono = Icons.Default.PersonAdd,
                        color = Colors.morado,
                        titulo = "Nuevo Proveedor",
                        subtitulo = "Agrega un nuevo proveedor de servicios al sistema",
                        onClick = onNuevoProveedor
                    ) {
                        Text("${resumen.totalProveedores} proveedores registrados", fontSize = 12.sp, color = Colors.textoSec)
                    }
                }

                // Footer
                Text(
                    "Sistema de Mantenimiento ${resumen.version} · ${resumen.edificio} · ${resumen.anio}",
                    fontSize = 12.sp,
                    color = Colors.textoSec,
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ==========================================
// COMPONENTES
// ==========================================



@Composable
private fun TarjetaAccion(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    color: Color,
    titulo: String,
    subtitulo: String,
    onClick: () -> Unit,
    dato: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(16.dp)).clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = Colors.card,
        border = BorderStroke(1.dp, Colors.borde),
        shadowElevation = 1.dp
    ) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Icono arriba
            Box(
                Modifier.size(56.dp).background(color.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier.size(32.dp).background(color, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icono, null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(Modifier.height(8.dp))

            Text(titulo, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Colors.texto)
            Text(subtitulo, fontSize = 13.sp, color = Colors.textoSec)

            Spacer(Modifier.weight(1f))

            // Dato + flecha al pie
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(shape = RoundedCornerShape(6.dp), color = color.copy(alpha = 0.08f)) {
                    Box(Modifier.padding(horizontal = 8.dp, vertical = 3.dp)) { dato() }
                }
                Spacer(Modifier.weight(1f))
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = color)
            }
        }
    }
}

// ==========================================
// UTILIDADES
// ==========================================

private fun saludo(hora: Int): String = when (hora) {
    in 5..11 -> "Buenos días"
    in 12..18 -> "Buenas tardes"
    else -> "Buenas noches"
}


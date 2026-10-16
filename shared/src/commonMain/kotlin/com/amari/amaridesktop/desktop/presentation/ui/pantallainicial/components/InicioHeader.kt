package com.amari.amaridesktop.desktop.presentation.ui.pantallainicial.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amari.amaridesktop.desktop.presentation.ui.pantallainicial.InicioResumen
import com.amari.amaridesktop.desktop.theme.Colors

@Composable
fun InicioHeader(resumen: InicioResumen, onNotificaciones: () -> Unit, onPerfil: () -> Unit) {
    Surface(color = Colors.card, border = BorderStroke(1.dp, Colors.borde)) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo: cuadrado blanco dentro de cuadrado azul redondeado
            Box(
                Modifier.size(40.dp).background(Colors.azul, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Box(Modifier.size(16.dp).background(Color.White, RoundedCornerShape(3.dp)))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("Sistema de Mantenimiento", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Colors.texto)
                Text(resumen.edificio, fontSize = 12.sp, color = Colors.textoSec)
            }

            // Notificaciones
            BadgedBox(
                badge = {
                    if (resumen.notificacionesPendientes > 0) {
                        Badge { Text("${resumen.notificacionesPendientes}") }
                    }
                }
            ) {
                IconButton(onClick = onNotificaciones) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = Colors.textoSec)
                }
            }

            Spacer(Modifier.width(12.dp))

            // Perfil
            Row(
                Modifier.clip(RoundedCornerShape(10.dp)).clickable(onClick = onPerfil).padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(36.dp).background(Colors.azul.copy(alpha = 0.12f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(iniciales(resumen.usuarioNombre, resumen.usuarioRol), fontSize = 13.sp,
                        fontWeight = FontWeight.Bold, color = Colors.azul)
                }
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(resumen.usuarioNombre, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Colors.texto)
                    Text(resumen.usuarioRol, fontSize = 11.sp, color = Colors.textoSec)
                }
            }
        }
    }
}

// "Admin" + "Administrador" -> "AD"; "Juan Pérez" -> "JP"
private fun iniciales(nombre: String, rol: String): String {
    val partes = nombre.trim().split(" ").filter { it.isNotBlank() }
    return when {
        partes.size >= 2 -> "${partes[0].first()}${partes[1].first()}".uppercase()
        partes.size == 1 && rol.isNotBlank() -> "${partes[0].first()}${rol.drop(1).first()}".uppercase()
        partes.size == 1 -> partes[0].take(2).uppercase()
        else -> "?"
    }
}
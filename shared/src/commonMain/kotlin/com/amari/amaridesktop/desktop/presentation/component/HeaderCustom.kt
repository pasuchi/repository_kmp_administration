package com.amari.amaridesktop.desktop.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amari.amaridesktop.desktop.presentation.MantColors


@Composable
fun Header(goBack: () -> Unit , title: String, subtitle: String = "") {
    Surface(color = MantColors.card, border = BorderStroke(1.dp, MantColors.borde)) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = goBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                Spacer(Modifier.width(4.dp))
                Text("Volver")
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MantColors.texto
                )
                Text(
                    "Gestión de Mantenimiento / Nuevo registro - Edificio Torre Norte",
                    fontSize = 12.sp,
                    color = MantColors.textoSec
                )
            }
        }
    }
}
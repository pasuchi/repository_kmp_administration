package com.amari.amaridesktop.desktop.presentation.component

import com.amari.amaridesktop.desktop.theme.Colors
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// LOADER: 3 puntos que rebotan
// ==========================================

@Composable
fun PuntosLoader(
    modifier: Modifier = Modifier,
    color: Color = Colors.primario,
    tamanoPunto: Int = 12
) {
    val transicion = rememberInfiniteTransition(label = "puntos")

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { indice ->
            val progreso by transicion.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0f at 0
                        1f at 300 using FastOutSlowInEasing
                        0f at 600 using FastOutSlowInEasing
                        0f at 1200
                    },
                    repeatMode = RepeatMode.Restart,
                    initialStartOffset = StartOffset(indice * 150)
                ),
                label = "punto$indice"
            )
            Box(
                Modifier
                    .size(tamanoPunto.dp)
                    .graphicsLayer { translationY = -progreso * 10.dp.toPx() }
                    .scale(0.8f + 0.2f * progreso)
                    .alpha(0.5f + 0.5f * progreso)
                    .background(color, CircleShape)
            )
        }
    }
}

// ==========================================
// OVERLAY: bloquea la pantalla mientras carga
// ==========================================

@Composable
fun LoginLoaderOverlay(
    visible: Boolean,
    mensaje: String = "Iniciando sesión..."
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f))
                // consume los clics para que no se toque nada por debajo
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                ),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Colors.superficie,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 36.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(Modifier.height(28.dp), contentAlignment = Alignment.BottomCenter) {
                        PuntosLoader()
                    }
                    Text(
                        mensaje,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Colors.texto
                    )
                }
            }
        }
    }
}
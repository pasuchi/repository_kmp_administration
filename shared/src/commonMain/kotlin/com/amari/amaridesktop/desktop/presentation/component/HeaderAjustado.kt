package com.amari.amaridesktop.desktop.presentation.component
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints

@Composable
fun HeaderAjustado(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    body: @Composable () -> Unit
) {
    SubcomposeLayout(modifier) { constraints ->
        // 1. Medir el cuerpo (rows de abajo) con su ancho natural
        val bodyPlaceable = subcompose("body", body).map {
            it.measure(constraints.copy(minWidth = 0, minHeight = 0))
        }
        val bodyWidth = bodyPlaceable.maxOfOrNull { it.width } ?: 0

        // 2. Medir el header forzando ese ancho
        val headerPlaceable = subcompose("header", header).map {
            it.measure(Constraints.fixedWidth(bodyWidth))
        }

        val headerHeight = headerPlaceable.sumOf { it.height }
        val bodyHeight = bodyPlaceable.sumOf { it.height }

        layout(bodyWidth, headerHeight + bodyHeight) {
            var y = 0
            headerPlaceable.forEach { it.place(0, y); y += it.height }
            bodyPlaceable.forEach { it.place(0, y); y += it.height }
        }
    }
}
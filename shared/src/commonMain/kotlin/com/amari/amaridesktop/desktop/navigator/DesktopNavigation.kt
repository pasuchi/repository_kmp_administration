package com.amari.amaridesktop.desktop.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.keepScreenOn
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.amari.amaridesktop.desktop.presentation.DetailEquipmentRoot
import com.amari.amaridesktop.desktop.presentation.EquipmentListScreen
import com.amari.amaridesktop.desktop.presentation.EquipoOpcion
import com.amari.amaridesktop.desktop.presentation.GestionMantenimientoScreen
import com.amari.amaridesktop.desktop.presentation.RegistrarMantenimientoScreen
import com.amari.amaridesktop.desktop.presentation.equiposDemo
import org.koin.compose.koinInject

@Composable
fun DesktopNavigation() {

    val navigator: Navigator = koinInject()

    NavDisplay(
        backStack = navigator.backStack,
        onBack = { navigator.pop() },
        entryProvider = entryProvider {
            entry<Home> {
                //GestionMantenimientoScreen(equipos = equiposDemo, aniosDisponibles = listOf(2026, 2025, 2024))
               // EquipmentListScreen(Modifier.keepScreenOn())

                RegistrarMantenimientoScreen(
                    equipos = listOf(
                        EquipoOpcion("ASC-01", "Ascensor Principal", 3),
                        EquipoOpcion("BOM-01", "Bomba de Agua", 6)
                    ),
                    fechaHoy = kotlinx.datetime.LocalDate(2026, 10, 13),
                    onVolver = { navigator.pop() },
                    onGuardar = { nuevo ->
                        println("Guardando mantenimiento para: ${nuevo.equipoId}")
                    }
                )
            }
            entry<DetailEquipment> { route ->
                //DetailEquipmentRoot(Modifier.keepScreenOn())
                RegistrarMantenimientoScreen(
                    equipos = listOf(
                        EquipoOpcion("ASC-01", "Ascensor Principal", 3),
                        EquipoOpcion("BOM-01", "Bomba de Agua", 6)
                    ),
                    fechaHoy = kotlinx.datetime.LocalDate(2026, 10, 13),
                    onVolver = { navigator.pop() },
                    onGuardar = { nuevo ->
                        println("Guardando mantenimiento para: ${nuevo.equipoId}")
                    }
                )
            }
        }
    )
}
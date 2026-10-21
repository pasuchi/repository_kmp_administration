package com.amari.amaridesktop.desktop.navigator

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.amari.amaridesktop.desktop.presentation.EquipoOpcion
import com.amari.amaridesktop.desktop.presentation.ui.gestionmantenimiento.GestionMantenimientoScreen
import com.amari.amaridesktop.desktop.presentation.RegistrarMantenimientoScreen
import com.amari.amaridesktop.desktop.presentation.ui.gestionmantenimiento.GestionMantenimientoRoot
import com.amari.amaridesktop.desktop.presentation.ui.gestionmantenimiento.equiposDemo
import com.amari.amaridesktop.desktop.presentation.ui.login.LoginScreenRoot
import com.amari.amaridesktop.desktop.presentation.ui.newprovider.NewProviderRoot
import com.amari.amaridesktop.desktop.presentation.ui.pantallainicial.InicioScreenRoot
import org.koin.compose.koinInject

@Composable
fun DesktopNavigation() {

    val navigator: Navigator = koinInject()

    NavDisplay(
        backStack = navigator.backStack,
        onBack = { navigator.pop() },
        entryProvider = entryProvider {
            entry<LoginRoot> {
                LoginScreenRoot()

            }

            entry<GestionMantenimiento> {
                GestionMantenimientoRoot()
            }

            entry<InicioRoot> {
                InicioScreenRoot()
            }

            entry<RegistrarMantenimiento> { route ->
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
            entry<NuevoProovedor> {
                NewProviderRoot()
            }
        }
    )
}
package com.amari.amaridesktop.desktop.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.amari.amaridesktop.desktop.presentation.EquipoOpcion
import com.amari.amaridesktop.desktop.presentation.GestionMantenimientoScreen
import com.amari.amaridesktop.desktop.presentation.ui.pantallainicial.InicioResumen
import com.amari.amaridesktop.desktop.presentation.ui.pantallainicial.InicioScreen
import com.amari.amaridesktop.desktop.presentation.RegistrarMantenimientoScreen
import com.amari.amaridesktop.desktop.presentation.equiposDemo
import com.amari.amaridesktop.desktop.presentation.ui.login.LoginScreen
import org.koin.compose.koinInject

@Composable
fun DesktopNavigation() {

    val navigator: Navigator = koinInject()

    NavDisplay(
        backStack = navigator.backStack,
        onBack = { navigator.pop() },
        entryProvider = entryProvider {
            entry<LoginRoot> {
                var cargando by remember { mutableStateOf(false) }
                var error by remember { mutableStateOf<String?>(null) }
                LoginScreen(
                    cargando = cargando,
                    errorServidor = error,
                    onIniciarSesion = { email, contrasena ->
                        println("Login: $email")
                        error = if (contrasena == "1234") null else "Email o contraseña incorrectos"
                    },
                    onOlvideContrasena = { println("Olvidé contraseña") }
                )
            }


            entry<InicioRoot> {


                InicioScreen(
                    resumen = InicioResumen(
                        edificio = "Edificio Torre Norte",
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
                        println("Abrir gestión de mantenimiento")
                    },
                    onRegistrarMantenimiento = {
                        println("Abrir registro de mantenimiento")
                    },
                    onNuevoProveedor = {
                        println("Abrir nuevo proveedor")
                    },
                    onNotificaciones = {
                        println("Abrir notificaciones")
                    },
                    onPerfil = {
                        println("Abrir perfil")
                    }
                )

            }


            entry<Home> {
                GestionMantenimientoScreen(
                    equipos = equiposDemo,
                    aniosDisponibles = listOf(2026, 2025, 2024)
                )
                // EquipmentListScreen(Modifier.keepScreenOn())
                /*
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

                 */

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
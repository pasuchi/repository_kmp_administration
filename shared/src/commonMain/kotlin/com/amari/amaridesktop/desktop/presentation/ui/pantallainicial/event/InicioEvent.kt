package com.amari.amaridesktop.desktop.presentation.ui.pantallainicial.event

sealed class InicioEvent {
    class GestionMantenimiento : InicioEvent()
    class RegistrarMantenimiento : InicioEvent()
    class NuevoProveedor : InicioEvent()
}
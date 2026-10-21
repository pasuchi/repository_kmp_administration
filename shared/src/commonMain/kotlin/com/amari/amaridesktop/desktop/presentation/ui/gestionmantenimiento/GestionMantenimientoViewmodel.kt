package com.amari.amaridesktop.desktop.presentation.ui.gestionmantenimiento

import androidx.lifecycle.ViewModel
import com.amari.amaridesktop.desktop.navigator.Navigator
import com.amari.amaridesktop.desktop.presentation.ui.gestionmantenimiento.event.GestionMantenimientoEvent

class GestionMantenimientoViewmodel(private val navigator: Navigator) : ViewModel() {

    fun onEvent(event: GestionMantenimientoEvent) {
        when (event) {
            is GestionMantenimientoEvent.GoBack -> navigator.pop()
        }
    }
}
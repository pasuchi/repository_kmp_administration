package com.amari.amaridesktop.desktop.presentation.ui.pantallainicial

import androidx.lifecycle.ViewModel
import com.amari.amaridesktop.desktop.navigator.GestionMantenimiento
import com.amari.amaridesktop.desktop.navigator.Navigator
import com.amari.amaridesktop.desktop.navigator.NuevoProovedor
import com.amari.amaridesktop.desktop.navigator.RegistrarMantenimiento
import com.amari.amaridesktop.desktop.presentation.ui.pantallainicial.event.InicioEvent

class InicioViewmodel (private val navigator: Navigator) : ViewModel() {


    fun onEvent(inicioEvent: InicioEvent){
        when(inicioEvent){
            is InicioEvent.GestionMantenimiento -> navigator.navigate(GestionMantenimiento)
            is InicioEvent.NuevoProveedor -> navigator.navigate(NuevoProovedor)
            is InicioEvent.RegistrarMantenimiento -> navigator.navigate(RegistrarMantenimiento)
        }
    }
}
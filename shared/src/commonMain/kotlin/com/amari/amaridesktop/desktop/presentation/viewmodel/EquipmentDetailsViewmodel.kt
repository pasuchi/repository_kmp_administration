package com.amari.amaridesktop.desktop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.amari.amaridesktop.desktop.navigator.Navigator

class EquipmentDetailsViewmodel(private val navigator: Navigator): ViewModel() {


    fun navigate (){

        navigator.pop()
    }

}
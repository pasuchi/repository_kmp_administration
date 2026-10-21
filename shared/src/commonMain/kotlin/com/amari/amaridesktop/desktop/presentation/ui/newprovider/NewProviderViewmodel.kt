package com.amari.amaridesktop.desktop.presentation.ui.newprovider

import androidx.lifecycle.ViewModel
import com.amari.amaridesktop.desktop.navigator.Navigator
import com.amari.amaridesktop.desktop.presentation.ui.newprovider.event.NewProviderEvent

class NewProviderViewmodel(private val navigator: Navigator) : ViewModel() {
    fun onEvent(newProviderEvent: NewProviderEvent){
        when(newProviderEvent){
            is NewProviderEvent.GoBack -> navigator.pop()
        }
    }
}
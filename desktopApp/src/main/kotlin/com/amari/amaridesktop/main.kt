package com.amari.amaridesktop

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.amari.amaridesktop.desktop.EquipmentListScreen
import com.amari.amaridesktop.desktop.di.DomainAdminMainKoinModule
import com.amari.amaridesktop.di.jvmNetworkModulde
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(DomainAdminMainKoinModule + jvmNetworkModulde)
    }



    Window(
        onCloseRequest = ::exitApplication,
        title = "AmariDesktop",
    ) {
        EquipmentListScreen(Modifier.fillMaxSize())
    }
}
package com.amari.amaridesktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.amari.amaridesktop.desktop.di.DomainAdminMainKoinModule
import com.amari.amaridesktop.desktop.navigator.DesktopNavigation
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
        DesktopNavigation()
    }
}
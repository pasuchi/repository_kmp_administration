package com.amari.amaridesktop.desktop.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amari.amaridesktop.desktop.component.ToolBarCustom
import com.amari.amaridesktop.desktop.presentation.viewmodel.EquipmentDetailsViewmodel
import com.amari.amaridesktop.desktop.presentation.viewmodel.EquipmentViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun DetailEquipmentRoot(modifier: Modifier) {
    val vm: EquipmentDetailsViewmodel = koinViewModel()

    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        ToolBarCustom("Equipos detalle", navigator = {
           vm.navigate()
        }, noArrow = true)

    }) { innerPadding ->

        Text(modifier = Modifier.padding(innerPadding), text = "HELLO WORLD")
    }
}
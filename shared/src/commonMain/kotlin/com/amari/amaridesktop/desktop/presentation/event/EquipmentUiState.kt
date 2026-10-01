package com.peru.admin_main_presentation.equipment

import com.amari.amaridesktop.desktop.domain.EquipmentMantListModel

data class EquipmentUiState(
    val listEquipment: List<EquipmentMantListModel> = emptyList()
)
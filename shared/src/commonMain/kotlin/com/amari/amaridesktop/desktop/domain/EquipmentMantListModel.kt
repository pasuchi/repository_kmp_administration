package com.amari.amaridesktop.desktop.domain

import kotlinx.serialization.Serializable

@Serializable

data class EquipmentMantListModel(
    val listEquipmentMantModel: List<EquipmentMantModel> = emptyList(),
    var oneObjecEquipment: EquipmentMantModel? = null,
    val equipmentCode: String = "",
    var daysLeft: String = "",
    var totalMonthLeft: String? = null,
    var totalRecolectMoney: String? = null,
    val yearStore: List<String> = emptyList(),
    var isExpanded:Boolean = false
)
package com.amari.amaridesktop.desktop.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EquipmentMantListResponse(
    @SerialName("listEquipmentMantRequest")
    val listEquipmentMantRequest: List<EquipmentMantRequest> = emptyList(),
    @SerialName("oneObjecEquipment")
    val oneObjecEquipment: EquipmentMantRequest? = null,
    @SerialName("equipmentCode")
    val equipmentCode: String,
    @SerialName("daysLeft")
    val daysLeft: String= "",
    @SerialName("totalMonths")
    val totalMonths: String ="",
    @SerialName("yearStore")
    val yearStore:List<String>)
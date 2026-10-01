package com.amari.amaridesktop.desktop.domain.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ParkingLotProprietaryModel(
    @SerialName("name")
    val name: String,
    @SerialName("m2")
    val m2: String,
    @SerialName("isEditable")
    val isEditable: Boolean = false,
    @SerialName("uniqueToken")
    val uniqueToken: String = "",
    @SerialName("order")
    val order: String

)
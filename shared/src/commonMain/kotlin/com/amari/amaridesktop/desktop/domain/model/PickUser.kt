package com.amari.amaridesktop.desktop.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PickUser(
    @SerialName("title")
    val title: String = "",
    @SerialName("amount")
    val amount: String = "",
    @SerialName("order")
    val order: String,
    @SerialName("department")
    val department:String
)
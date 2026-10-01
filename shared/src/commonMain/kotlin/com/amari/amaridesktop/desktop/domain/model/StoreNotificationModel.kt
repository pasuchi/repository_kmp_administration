package com.amari.amaridesktop.desktop.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreNotificationModel(
    @SerialName("title")
    val title:String = "",
    @SerialName("content")
    val content:String = "",
    @SerialName("day")
    val day : String="",
    @SerialName("month")
    val month : String="",
    @SerialName("year")
    val year : String="",
    @SerialName("hours")
    val hours :String = ""
)
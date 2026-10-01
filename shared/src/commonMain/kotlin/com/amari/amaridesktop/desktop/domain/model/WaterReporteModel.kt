package com.amari.amaridesktop.desktop.domain.model

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.SerialName
@kotlinx.serialization.Serializable
data class WaterReporteModel(
    @SerialName("urlPhotoIsChange")
    var urlPhotoIsChange: String,
    @SerialName("contometerIsChange")
    var contometerIsChange: Boolean? = null,
    @SerialName("uniqueIde")
    val uniqueIde: String,
    @SerialName("urlPhoto")
    val urlPhoto: String,
    @SerialName("registerWater")
    var registerWater: String,
    @SerialName("timestamp")
    val timestamp: Timestamp,
    @SerialName("monthConsume")
    var monthConsume: String? = "",
    @SerialName("department")
    var department: String = "",
    @SerialName("reasonUpdateContometer")
    var reasonUpdateContometer: String = "",
    @SerialName("newMonthConsume")
    var newMonthConsume: String? = "",
    var pastRegisterWater: String? = "",
    var month: String? = "",
)
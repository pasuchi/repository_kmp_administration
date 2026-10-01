package com.amari.amaridesktop.desktop.domain.model

import kotlinx.serialization.Serializable
@Serializable
data class WaterReportUiModel(
    val uniqueIde: String,
    val urlPhoto: String,
    val registerWater: String,
    var monthConsume: String? = "",
    var department: String = "",
    var previewRegisterWater:String? = "",
    var totalWater:Double? = 0.0,
    var pricePeerM3:Double? = 0.0,
    var waterAmountCharge:Double? = 0.0,
    )
package com.amari.amaridesktop.desktop.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class EquipmentAmariModel(
    val recaudado: String? = "",
    val monto: String,
    var order: String,
    val periodo: String,
    var equipmentName: String,
    val code:String? ="",
    var uniqueCode: String? = "",
    var isColorGray: Boolean = false,
    var distributionPayment: DistributionPayment = DistributionPayment.percent
)
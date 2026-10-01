package com.amari.amaridesktop.desktop.domain

import kotlinx.serialization.Serializable
@Serializable
data class EquipmentMantModel(
    val uniqueKey: String,
    val equipmentName: String,
    val supplier: String,
    val payAmount: String,
    val currency: String,
    val monthName:String,
    val maintenanceDay: String,
    val typeMaintenance: String,
    val equipmentPeriod: String,
    val equipmentNumber: String,
    var urlListQuotes: List<String>,
    val urlListRecibos: List<String>,
    val urlListInformes: List<String>,
    val urlListExtorno: List<String>,
    val isRefund: Boolean,
    val amountRefund:String,
    val insertWhyRefund:String,
    val isFreeMaintenance:Boolean = false,
    val nextMaintenanceDay: String? = null,
    val reason: String? = null,)
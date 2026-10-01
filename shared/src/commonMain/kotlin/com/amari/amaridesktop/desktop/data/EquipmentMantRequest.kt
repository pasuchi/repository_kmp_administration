package com.amari.amaridesktop.desktop.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EquipmentMantRequest(
    @SerialName("uniqueKey")
    val uniqueKey: String?,
    @SerialName("equipmentName")
    val equipmentName: String,
    @SerialName("equipmentNumber")
    val equipmentNumber: String,
    @SerialName("supplier")
    val supplier: String,
    @SerialName("payAmount")
    val payAmount: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("maintenanceDay")
    val maintenanceDay: String,
    @SerialName("typeMaintenance")
    val typeMaintenance: String,
    @SerialName("equipmentPeriod")
    val equipmentPeriod: String,
    @SerialName("refund")
    var isRefund: Boolean? = false,
    @SerialName("amountRefund")
    var amountRefund: String? ="",
    @SerialName("insertWhyRefund")
    var insertWhyRefund: String? = "",
    @SerialName("nextMaintenanceDay")
    val nextMaintenanceDay: String,
    @SerialName("reason")
    val reason: String?,
    @SerialName("isFreeMaintenance")
    val isFreeMaintenance: Boolean? = false,
    @SerialName("urlListCotizaciones")
    val urlListCotizaciones: List<String>? = emptyList(),
    @SerialName("urlListInformes")
    var urlListInformes: List<String>? = emptyList(),
    @SerialName("urlListRecibos")
    var urlListRecibos: List<String>? = emptyList(),
    @SerialName("urlListExtornos")
    var urlListExtornos: List<String>? = emptyList()
)
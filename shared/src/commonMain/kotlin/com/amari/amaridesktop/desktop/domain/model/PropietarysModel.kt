package com.amari.amaridesktop.desktop.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PropietarysModel(
    @SerialName("enableBooking")
    val enableBooking: Boolean? = false,
    @SerialName("isSell")
    val isSell: Boolean? = null,
    @SerialName("m2")
    val m2: String? = "",
    @SerialName("nombrePropietario")
    var nombrePropietario: String ="",
    @SerialName("password")
    var psw: String ="",
    @SerialName("tokenUnique")
    val tokenUnique: String ="",
    @SerialName("userid")
    val userid: String = "",
    @SerialName("order")
    val order: String = "",
    @SerialName("number")
    var phoneNumber: List<String>? = null,
    @SerialName("email")
    var email: List<String>? = null,
    @SerialName("depositList")
    var depositList: List<DepositProprietaryModel>? = emptyList(),
    @SerialName("parkingLot")
    var parkingLot: List<ParkingLotProprietaryModel>? = emptyList(),
    @SerialName("dateBuy")
    var dateBuy: String? = null,
    )
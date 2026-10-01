package com.amari.amaridesktop.desktop.domain.model

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.SerialName

data class BookingModel(
    @SerialName("place")
    var place: String,
    @SerialName("codeAreaCommon")
    var codeAreaCommon: String?,
    @SerialName("dateReserve")
    var dateReserve: String,
    @SerialName("namePropietary")
    val namePropietary: String,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("department")
    val department: String,
    @SerialName("colorLine")
    var colorLine: String,
    @SerialName("tokenUnique")
    val tokenUnique: String?,
    @SerialName("cleaningInclude")
    val cleaningInclude: Boolean,
    @SerialName("warranty")
    var warranty: Boolean? = null,
    @SerialName("warrantyAmount")
    val warrantyAmount: String? = "",
    @SerialName("amount")
    val amount: String = "",
    @SerialName("bankDestinyToDeposit")
    var bankDestinyToDeposit: String? = "",
    @SerialName("bankDestinyOperationCode")
    var bankDestinyOperationCode: String? = "",
    @SerialName("bankDestinyAccountNumber")
    var bankDestinyAccountNumber: String? = "",
    @SerialName("bankDestinyOwnerNameAccount")
    var bankDestinyOwnerNameAccount: String? = "",
    @SerialName("uriImageOperationNumber")
    var uriImageOperationNumber: String = "",
    @SerialName("comments")
    var comments: String = "",
    @SerialName("state")
    var state: String = "",
    @SerialName("bankOriginDeposit")
    var bankOriginDeposit: String = "",
    @SerialName("bankOriginOperationCode")
    var bankOriginOperationCode: String = "",
    @SerialName("timeStamp")
    var timeStamp: Timestamp = Timestamp.now())
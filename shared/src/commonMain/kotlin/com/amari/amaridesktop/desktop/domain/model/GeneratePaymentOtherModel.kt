package com.amari.amaridesktop.desktop.domain.model

import com.api.core.domain.model.item_add.ItemOneModel
import com.api.core.domain.model.item_add.ItemTwoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeneratePaymentOtherModel(
    @SerialName("uniqueCode")
    val uniqueCode:String,
    @SerialName("type")
    val type :String,
    @SerialName("userId")
    val userId:String,
    @SerialName("m2")
    val m2:String,
    @SerialName("namePropietary")
    val namePropietary:String,
    @SerialName("order")
    val order:String,
    @SerialName("waterReport")
    var waterReport: WaterReportUiModel? = null,
    @SerialName("listDetailsProprietary")
    var listDetailsProprietary :MutableList<ItemOneModel>? = null,
    @SerialName("sumTotalM2")
    var sumTotalM2 :String? ="",
    @SerialName("totalParticiapation")
    var totalParticiapation :String? ="",
    @SerialName("monthBilling")
    var monthBilling:String? ="",
    @SerialName("yearBilling")
    var yearBilling:String? ="",
    @SerialName("equipmentAndAnotherThingsList")
    var equipmentAndAnotherThingsList :List<ItemTwoModel>? = emptyList(),
    @SerialName("listPickProprietarys")
    val listPickProprietarys: List<PickUser> = emptyList(),
    @SerialName("listAnotherChargeThings")
    val listAnotherChargeThings: List<ItemTwoModel> = emptyList(),
    )
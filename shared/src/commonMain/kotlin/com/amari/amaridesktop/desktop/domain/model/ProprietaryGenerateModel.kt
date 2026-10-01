package com.amari.amaridesktop.desktop.domain.model

import com.api.core.domain.model.item_add.ItemOneModel
import com.api.core.domain.model.item_add.ItemTwoModel

@kotlinx.serialization.Serializable
data class ProprietaryGenerateModel(
    val enableBooking: Boolean? = false,
    val isSell: Boolean? = null,
    val m2: String? = "",
    var nombrePropietario: String = "",
    var psw: String = "",
    val tokenUnique: String = "",
    val userid: String = "",
    val order: String = "",
    var phoneNumber: List<String>? = null,
    var email: List<String>? = null,
    var depositList: List<DepositProprietaryModel>? = emptyList(),
    var parkingLot: List<ParkingLotProprietaryModel>? = emptyList(),
    var dateBuy: String? = null,
    var waterReport: WaterReportUiModel? = null,
    var listDetailsProprietary :MutableList<ItemOneModel>? = null,
    var equipmentAndAnotherThingsList :List<ItemTwoModel>? = emptyList(),
    var sumTotalM2 :String? ="",
    var totalParticiapation :String? ="",
    var monthBilling:String? ="",
    var yearBilling:String? ="",
    val listPickProprietarys: List<PickUser> = emptyList(),
    val listAnotherChargeThings: List<ItemTwoModel> = emptyList(),
    var equipmnetChargeBuilding :List<ItemTwoModel>? = emptyList()

    )
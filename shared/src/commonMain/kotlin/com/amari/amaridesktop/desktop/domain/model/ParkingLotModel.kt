package com.api.core.domain.model

data class ParkingLotModel(
    val nameParking: String,
    val m2: String,
    val order: String,
    val isTakeIt: Boolean = false,
    val propietarieCode: String? = null,
    val namePropietarie:String? = null,
    val uniqueCode: String? = null,
    val mapToStore: HashMap<String, Any> = hashMapOf()
)
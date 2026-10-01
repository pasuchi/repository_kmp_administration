package com.amari.amaridesktop.desktop.domain.model

data class DepositModel(
    val nameDeposit: String,
    val m2: String,
    val order: String,
    val isTakeIt: Boolean = false,
    val uniqueCode: String,
    val propietarieCode :String = "",
    val namePropietarie :String = "",
    val mapToStore: HashMap<String, Any> = hashMapOf()
)
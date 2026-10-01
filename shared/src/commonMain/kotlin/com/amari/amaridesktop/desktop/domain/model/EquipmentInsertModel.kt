package com.amari.amaridesktop.desktop.domain.model

data class EquipmentInsertModel(
    val name: String,
    val period: String,
    val amount: String,
    val order: String,
    val uniqueCode: String,
    val mapToStore: HashMap<String, Any>,
    val code :String
)
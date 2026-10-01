package com.amari.amaridesktop.desktop.domain.model

data class StoreBillsModel(
    val comments: String,
    val date: String,
    val amountBills: String,
    val urlDocument: List<String>
)
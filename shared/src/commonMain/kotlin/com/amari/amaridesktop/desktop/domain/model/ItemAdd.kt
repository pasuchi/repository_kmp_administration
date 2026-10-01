package com.amari.amaridesktop.desktop.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ItemAdd(
    val title: String = "",
    val amount: String = "",
    val order: String,
    val isEditable: Boolean = false,
    val disableBox: Boolean = false,
    val enableDeleteIcon: Boolean = false,
    val code: String = "",
    val distributionPayment: DistributionPayment = DistributionPayment.percent
)
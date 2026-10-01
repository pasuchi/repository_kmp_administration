package com.api.core.domain.model.item_add

import kotlinx.serialization.Serializable

@Serializable
data class ItemTwoModel(
    val order: String,
    val nameItem: String,
    val amount: String,
    val isGrayColor: Boolean
)
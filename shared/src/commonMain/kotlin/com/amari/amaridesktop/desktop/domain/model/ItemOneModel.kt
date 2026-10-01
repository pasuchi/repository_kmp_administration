package com.api.core.domain.model.item_add

import kotlinx.serialization.Serializable

@Serializable
data class ItemOneModel(val property: String, val codigo: String, val area: String)
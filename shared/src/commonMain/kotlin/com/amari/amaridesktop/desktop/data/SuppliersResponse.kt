package com.api.core.domain.supplier

import kotlinx.serialization.SerialName

data class SuppliersResponse(
    @SerialName("name")
    val name: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("specialist")
    val specialist :String)
package com.amari.amaridesktop.desktop.domain.model

import com.amari.amaridesktop.resources.Res
import com.amari.amaridesktop.resources.bomba_agua
import kotlinx.serialization.SerialName
import org.jetbrains.compose.resources.DrawableResource

data class EquipmentModel(
    @SerialName("description")
    val description: String,
    @SerialName("amount")
    val amount: String,
    @SerialName("drawableItem")
    val drawableItem: DrawableResource = Res.drawable.bomba_agua
)
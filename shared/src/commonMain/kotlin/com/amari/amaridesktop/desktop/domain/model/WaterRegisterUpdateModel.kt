package com.amari.amaridesktop.desktop.domain.model

import coil3.Uri

data class WaterRegisterUpdateModel(
    val moth:String,
    val year:String,
    val uri: Uri?,
    val department: String,
    val tokenUnique: String,
    val newRegisterWater: String,
    val monthConsume: String,
    val reasonUpdateContometer: String,
    )
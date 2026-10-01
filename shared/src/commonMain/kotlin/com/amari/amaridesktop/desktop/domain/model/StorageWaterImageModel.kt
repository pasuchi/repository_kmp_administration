package com.amari.amaridesktop.desktop.domain.model

import coil3.Uri

data class StorageWaterImageModel(
    val uri: Uri,
    val namePhoto: String,
    val department: String,
    val tokenUnique: String,
    val registerWater :String)
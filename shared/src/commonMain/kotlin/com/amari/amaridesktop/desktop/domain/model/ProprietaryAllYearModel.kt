package com.amari.amaridesktop.desktop.domain.model

data class ProprietaryAllYearModel(
    val year:String="",
    val proprietaryGeneralMainList : List<ProprietaryGeneralMain> = emptyList()
)
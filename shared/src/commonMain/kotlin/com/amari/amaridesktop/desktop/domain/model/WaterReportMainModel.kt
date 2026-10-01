package com.amari.amaridesktop.desktop.domain.model

data class WaterReportMainModel(
    val waterReportModelList: List<WaterReporteModel>,
    val listDepartmentHasNoContometerReport: List<String>
)
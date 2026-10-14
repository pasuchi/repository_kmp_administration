package com.amari.amaridesktop.desktop.util

import com.amari.amaridesktop.desktop.domain.EquipmentMantListModel
import com.amari.amaridesktop.desktop.domain.EquipmentMantModel
import com.amari.amaridesktop.desktop.domain.model.TypeMaintenanceEquipment


val dummyEquipmentList: List<EquipmentMantListModel> = List(5) {
    val model = EquipmentMantModel(
        uniqueKey = "EQ-001",
        equipmentName = "Aire Acondicionado - Salón Principal",
        supplier = "Servitec SAC",
        payAmount = "350.00",
        currency = "SOLES",
        monthName = "Octubre",
        maintenanceDay = "15/09/2026",
        typeMaintenance = TypeMaintenanceEquipment.PREVENTIVO.nameEquipment,
        equipmentPeriod = "3",
        equipmentNumber = "AC-1001",
        urlListQuotes = listOf("https://example.com/cotizacion1.pdf"),
        urlListRecibos = listOf("https://example.com/recibo1.pdf"),
        urlListInformes = listOf("https://example.com/informe1.pdf"),
        urlListExtorno = emptyList(),
        isRefund = false,
        amountRefund = "0.00",
        insertWhyRefund = "",
        isFreeMaintenance = false,
        nextMaintenanceDay = "15/12/2026",
        reason = null
    )
    EquipmentMantListModel(
        listEquipmentMantModel = listOf(model),
        oneObjecEquipment = model, // directo, sin .map ni asignación posterior
        equipmentCode = "AC-1001",
        daysLeft = "-5",           // activa el badge rojo
        totalMonthLeft = "0",
        totalRecolectMoney = "1050.00",
        yearStore = listOf("2025", "2026"),
        isExpanded = false
    )
}
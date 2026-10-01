package com.peru.admin_main_presentation.equipment

import com.amari.amaridesktop.desktop.domain.EquipmentMantListModel


sealed class EquipmentListEvent {

    class FilterEquipments():EquipmentListEvent()
    class FilterExpiredEquipments():EquipmentListEvent()
    class filterNextEquipments():EquipmentListEvent()
    class GoToCalculator():EquipmentListEvent()
    class GetDetailMant(val equipmentMantListModel: EquipmentMantListModel):EquipmentListEvent()
}
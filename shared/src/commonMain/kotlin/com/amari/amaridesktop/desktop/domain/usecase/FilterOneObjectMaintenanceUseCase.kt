package com.amari.amaridesktop.desktop.domain.usecase

import com.amari.amaridesktop.desktop.domain.EquipmentMantModel
import com.amari.amaridesktop.desktop.domain.model.TypeMaintenanceEquipment
import kotlinx.datetime.LocalDate

class FilterOneObjectMaintenanceUseCase {

    operator fun invoke(
        listEquipmentMaintenance: List<EquipmentMantModel>,
        typeMaintenanceEquipment: TypeMaintenanceEquipment
    ):EquipmentMantModel? {
        val localDateLast = listEquipmentMaintenance.getlastNextMaintenanceDateEquipment(typeMaintenanceEquipment)
        return listEquipmentMaintenance.find {
            LocalDate.parse(it.nextMaintenanceDay.orEmpty()) == localDateLast
        }
    }
    private fun List<EquipmentMantModel>.getlastNextMaintenanceDateEquipment(typeMaintenanceEquipment: TypeMaintenanceEquipment): LocalDate? {
        var localdate: LocalDate? = null
        this.map {
            val local = LocalDate.parse(it.nextMaintenanceDay.orEmpty())
            if (localdate == null) {
                localdate = local
            } else {
                if (localdate!! < local) {
                    when(typeMaintenanceEquipment){
                        TypeMaintenanceEquipment.PREVENTIVO-> {
                            if (it.typeMaintenance == TypeMaintenanceEquipment.PREVENTIVO.nameEquipment){
                                localdate = local
                            }
                        }
                        TypeMaintenanceEquipment.CORRECTIVO -> {
                            if (it.typeMaintenance == TypeMaintenanceEquipment.CORRECTIVO.nameEquipment){
                                localdate = local
                            }
                        }
                        TypeMaintenanceEquipment.ALL -> {
                            localdate = local
                        }
                    }
                }
            }
        }
        return localdate
    }

}
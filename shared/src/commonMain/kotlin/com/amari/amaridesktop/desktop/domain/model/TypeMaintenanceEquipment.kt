package com.amari.amaridesktop.desktop.domain.model

enum class TypeMaintenanceEquipment(val nameEquipment: String) {

    PREVENTIVO("Preventivo"),
    CORRECTIVO("Correctivo"),
    ALL("ambos");

    companion object {
        infix fun from(value: String): TypeMaintenanceEquipment =
            values().firstOrNull { it.nameEquipment == value }?:ALL

    }

}

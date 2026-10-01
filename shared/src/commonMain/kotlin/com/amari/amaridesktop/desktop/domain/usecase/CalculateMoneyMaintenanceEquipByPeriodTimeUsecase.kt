package com.amari.amaridesktop.desktop.domain.usecase

import com.amari.amaridesktop.desktop.domain.EquipmentMantListModel
import com.amari.amaridesktop.desktop.util.roundTo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.math.abs
import kotlinx.datetime.Clock
class CalculateMoneyMaintenanceEquipByPeriodTimeUsecase {


    operator fun invoke(equipmentMantListModel: EquipmentMantListModel): String {
        val ss =
            equipmentMantListModel.oneObjecEquipment?.payAmount?.removeUnnecessaryComma()
                ?.toDouble()!! / equipmentMantListModel.oneObjecEquipment?.equipmentPeriod?.toDouble()!!
        return (ss * (calculateTotalMonth(
            equipmentMantListModel
        ))).roundTo(2).toString()
    }


    fun calculateTotalMonth(equipmentMantListModel: EquipmentMantListModel): Int {
        val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val totalMonth = equipmentMantListModel.totalMonthLeft?.toInt() ?: 0
        val equipmentPeriod =
            equipmentMantListModel.oneObjecEquipment?.equipmentPeriod?.toInt() ?: 0

        val date = LocalDate.parse(equipmentMantListModel.oneObjecEquipment?.maintenanceDay.orEmpty())
        when {
            totalMonth < 0 -> {
                return equipmentPeriod + abs(totalMonth)
            }

            totalMonth > 0 -> {
                return equipmentPeriod - totalMonth
            }

            equipmentPeriod == 1 &&  date.dayOfMonth in 1..today.dayOfMonth ->{
                return equipmentPeriod
            }

            totalMonth == 0 -> {
                return equipmentPeriod
            }


        }

        return 0
    }

    private fun String?.removeUnnecessaryComma() = this?.replace(",", "")

}
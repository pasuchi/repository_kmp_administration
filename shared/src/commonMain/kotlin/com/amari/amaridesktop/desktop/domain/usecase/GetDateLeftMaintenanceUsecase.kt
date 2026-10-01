package com.amari.amaridesktop.desktop.domain.usecase

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayIn
import kotlinx.datetime.Clock
class GetDateLeftMaintenanceUsecase  {

    operator fun invoke(localDate: LocalDate?): String {
        if (localDate == null) return "0"
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        return today.daysUntil(localDate).toString()
    }
}
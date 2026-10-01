package com.amari.amaridesktop.desktop.domain.usecase

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.datetime.Clock
import kotlin.time.ExperimentalTime

class GetTotalMonthLeftUsecase{

    @OptIn(ExperimentalTime::class)
    operator fun invoke(localDate: LocalDate): String {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val totalMonths = (localDate.year - today.year) * 12 +
                (localDate.monthNumber - today.monthNumber)

        return totalMonths.toString()
    }
}
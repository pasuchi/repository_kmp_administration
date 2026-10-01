package com.amari.amaridesktop.desktop.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.isoDayNumber

const val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"
const val pattern2 = "yyyy-MM-dd"

// Reemplazo simple de YearMonth (no existe en kotlinx-datetime)
data class YearMonth(val year: Int, val monthNumber: Int) {
    companion object {
        fun of(year: Int, monthNumber: Int) = YearMonth(year, monthNumber)
        fun now(): YearMonth {
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
            return YearMonth(today.year, today.monthNumber)
        }
    }
}

class DateUtilImpl : DateUtil {
    companion object {
        val monthString = linkedMapOf(
            "1" to "Enero", "2" to "Febrero", "3" to "Marzo", "4" to "Abril",
            "5" to "Mayo", "6" to "Junio", "7" to "Julio", "8" to "Agosto",
            "9" to "Septiembre", "10" to "Octubre", "11" to "Noviembre", "12" to "Diciembre"
        )
        val monthEnglishString = linkedMapOf(
            "1" to "January", "2" to "February", "3" to "March", "4" to "April",
            "5" to "May", "6" to "June", "7" to "July", "8" to "August",
            "9" to "September", "10" to "October", "11" to "November", "12" to "December"
        )
        val spanishToEnglishMonths = mapOf(
            "enero" to "January", "febrero" to "February", "marzo" to "March",
            "abril" to "April", "mayo" to "May", "junio" to "June",
            "julio" to "July", "agosto" to "August", "septiembre" to "September",
            "octubre" to "October", "noviembre" to "November", "diciembre" to "December"
        )
        val numberMonthByNameEnglish = mapOf(
            "January" to "1", "February" to "2", "March" to "3", "April" to "4",
            "May" to "5", "June" to "6", "July" to "7", "August" to "8",
            "September" to "9", "October" to "10", "November" to "11", "December" to "12"
        )
        val numberMonthByNameSpanish = mapOf(
            "enero" to "1", "febrero" to "2", "marzo" to "3", "abril" to "4",
            "mayo" to "5", "junio" to "6", "julio" to "7", "agosto" to "8",
            "septiembre" to "9", "octubre" to "10", "noviembre" to "11", "diciembre" to "12"
        )
    }

    // Extrae solo la parte "yyyy-MM-dd" de un string, sin importar si trae hora/timezone
    private fun parseDatePart(input: String?): LocalDate {
        val raw = input?.substring(0, 10) ?: throw IllegalArgumentException("Fecha nula")
        return LocalDate.parse(raw)
    }

    override fun getAllMonthOnYear(): List<String> =
        monthString.entries.sortedBy { it.key.toInt() }.map { it.value }

    override fun getYear(patternFormat: String, input: String?): String {
        val date = parseDatePart(input)
        return date.year.toString()
    }

    override fun getMothValueName(patternFormat: String, input: String?): String {
        val date = parseDatePart(input)
        return monthString[date.monthNumber.toString()].orEmpty()
    }

    override fun getMonthByNumber(number: String): String =
        monthString[number].toString()

    override fun getYearMonth(patternFormat: String, input: String): YearMonth {
        return try {
            val date = parseDatePart(input)
            YearMonth.of(date.year, date.monthNumber)
        } catch (e: Exception) {
            YearMonth.now()
        }
    }

    override fun getYearMonthDay(patternFormat: String, input: String): YearMonth {
        return try {
            val date = parseDatePart(input)
            YearMonth.of(date.year, date.monthNumber)
        } catch (e: Exception) {
            YearMonth.now()
        }
    }

    override fun convertSpanishToEnglish(month: String): String {
        val result = spanishToEnglishMonths[month.lowercase()]
        return result ?: month
    }

    override fun getDay(patternFormat: String, input: String?): String {
        val date = parseDatePart(input)
        return date.dayOfMonth.toString()
    }

    override fun getLocalDate(patternFormat: String, input: String?): LocalDate {
        return parseDatePart(input)
    }

    override fun getMonthByNumberEnglish(number: String): String =
        monthEnglishString[number].toString()

    override fun getNumberByMonthEnglish(month: String): String? =
        numberMonthByNameEnglish[month]

    override fun getNumberByMonthSpanish(month: String): String? =
        numberMonthByNameSpanish[month.lowercase()]

    override fun getStartOfLastWeek(): LocalDate {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val lastWeekAnchor = today.minus(DatePeriod(days = 7))
        val daysFromMonday = lastWeekAnchor.dayOfWeek.isoDayNumber - DayOfWeek.MONDAY.isoDayNumber
        return lastWeekAnchor.minus(DatePeriod(days = daysFromMonday))
    }

    override fun getEndOfLastWeek(): LocalDate {
        val startOfLastWeek = getStartOfLastWeek()
        return startOfLastWeek.plus(DatePeriod(days = 6))
    }
}
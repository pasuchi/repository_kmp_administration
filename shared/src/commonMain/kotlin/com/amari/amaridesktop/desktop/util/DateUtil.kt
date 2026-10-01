package com.amari.amaridesktop.desktop.util

import kotlinx.datetime.LocalDate

interface DateUtil {

    fun getAllMonthOnYear():List<String>
    fun getYear(patternFormat: String, input: String?): String
    fun getMothValueName(patternFormat: String, input: String?): String
    fun getMonthByNumber(number:String): String
    fun getYearMonth(patternFormat: String,input:String): YearMonth
    fun getYearMonthDay(patternFormat: String,input:String): YearMonth

    fun convertSpanishToEnglish(month:String):String

    fun getDay(patternFormat: String, input: String?): String
    fun getLocalDate(patternFormat: String, input: String?): LocalDate
    fun getMonthByNumberEnglish(number:String): String
    fun getStartOfLastWeek(): LocalDate
    fun getNumberByMonthEnglish(month:String) :String?
    fun getEndOfLastWeek(): LocalDate
    fun getNumberByMonthSpanish(month:String):String?

}
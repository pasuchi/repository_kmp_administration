package com.amari.amaridesktop.desktop.util

import kotlin.math.pow
import kotlin.math.round

fun Double.roundTo(decimals: Int): Double {
    return try {
        val factor = 10.0.pow(decimals)
        round(this * factor) / factor
    } catch (e: Exception) {
        0.0
    }
}
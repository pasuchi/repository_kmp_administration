package com.amari.amaridesktop.desktop.domain.model

sealed class ErrorBooking {
    object HasNoValues : ErrorBooking()
    object FatalError : ErrorBooking()

}
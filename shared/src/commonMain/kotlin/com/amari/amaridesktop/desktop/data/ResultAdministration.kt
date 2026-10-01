package com.amari.amaridesktop.desktop.data

sealed class ResultAdministration<T, U> {
    data class Success<T, U>(val value: T) : ResultAdministration<T, U>()
    data class Error<T, U>(val value: U) : ResultAdministration<T, U>()
}
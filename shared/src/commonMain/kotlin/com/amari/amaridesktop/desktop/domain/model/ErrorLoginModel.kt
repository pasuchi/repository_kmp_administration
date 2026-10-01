package com.amari.amaridesktop.desktop.domain.model

sealed class ErrorLoginModel {
    object NoUserFound : ErrorLoginModel()
    class Exception(val error: String) : ErrorLoginModel()

}
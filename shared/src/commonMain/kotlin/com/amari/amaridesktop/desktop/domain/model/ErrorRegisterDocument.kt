package com.amari.amaridesktop.desktop.domain.model

sealed class ErrorRegisterDocument {
    object Error :ErrorRegisterDocument()
    object AlreadyRegister :ErrorRegisterDocument()
}
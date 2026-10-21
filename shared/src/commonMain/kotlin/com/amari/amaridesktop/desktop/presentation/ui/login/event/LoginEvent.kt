package com.amari.amaridesktop.desktop.presentation.ui.login.event

sealed class LoginEvent {

    data class Login(val user:String, val password: String) : LoginEvent()


}
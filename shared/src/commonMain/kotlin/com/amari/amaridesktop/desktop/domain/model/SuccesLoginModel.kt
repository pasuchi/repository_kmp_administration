package com.amari.amaridesktop.desktop.domain.model

sealed class SuccesLoginModel {
    data class SuccessUser(val userID:String,val uniqueToken:String,val nameUser:String) : SuccesLoginModel()
    data class SuccessAdminUser(val userID:String,val uniqueToken:String,val nameUser:String) :
        SuccesLoginModel()
}
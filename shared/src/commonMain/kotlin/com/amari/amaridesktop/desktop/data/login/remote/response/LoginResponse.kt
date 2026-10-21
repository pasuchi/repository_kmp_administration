package com.amari.amaridesktop.desktop.data.login.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("message") val message: String = "",
    @SerialName("userId") val userId: String = "",
    @SerialName("email") val email: String = "",
    @SerialName("isadmin") val isAdmin: Boolean = false
)
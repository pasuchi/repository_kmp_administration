package com.amari.amaridesktop.desktop.data.login.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoginRequest (
    @SerialName("id") val id: String = "",
    @SerialName("password") val password: String = "",

)
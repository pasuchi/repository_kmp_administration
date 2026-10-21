package com.amari.amaridesktop.desktop.data.login.remote

import com.amari.amaridesktop.desktop.data.login.remote.request.LoginRequest
import com.amari.amaridesktop.desktop.data.login.remote.response.LoginResponse
import com.amari.amaridesktop.desktop.data.wrapper.ApiClient
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class LoginApi(private val api: ApiClient) {

    suspend fun callLogin(loginRequest: LoginRequest): Result<LoginResponse> = api.request(
        method = HttpMethod.Post,
        url = "auth/login",
    ) {
        contentType(ContentType.Application.Json)
        setBody(loginRequest)
    }
}
/*
val equipmentResult: Result<List<EquipmentResponse>> = apiClient.request(
    method = HttpMethod.Post,
    url = "${baseUrl}equipment/api/getequipment",
    typeInfo = typeInfoOf<List<EquipmentResponse>>()
) { ... }
Ejemplo de list
 */
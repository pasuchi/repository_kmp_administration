package com.amari.amaridesktop.desktop.data.remote

import com.amari.amaridesktop.desktop.data.EquipmentMantListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class EquipmentApi(private val httpClient: HttpClient) {
    private val baseUrl = "https://mi-api-drive-pead6uzojq-tl.a.run.app"

    suspend fun getMantenimientos(): List<EquipmentMantListResponse> {
        return httpClient.get("$baseUrl/mantenimientos").body()
    }
}
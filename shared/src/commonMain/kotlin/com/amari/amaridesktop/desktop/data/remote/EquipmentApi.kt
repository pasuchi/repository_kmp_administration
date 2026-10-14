package com.amari.amaridesktop.desktop.data.remote

import com.amari.amaridesktop.desktop.data.EquipmentMantListResponse
import com.amari.amaridesktop.desktop.data.remote.reponse.EquipmentResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class EquipmentApi(private val httpClient: HttpClient) {
    private val baseUrl = "https://mi-api-drive-470163071363.us-central1.run.app"

    suspend fun getMantenimientos(): List<EquipmentMantListResponse> {
        return httpClient.get("$baseUrl/mantenimientos").body()
    }

    suspend fun getMantenimientosKMP(): List<EquipmentResponse> {
        return httpClient.get("$baseUrl/equipment/api/getequipment").body()
    }

}
package com.amari.amaridesktop.desktop.data.remote

import com.amari.amaridesktop.desktop.data.EquipmentMantListResponse
import com.amari.amaridesktop.desktop.data.remote.reponse.EquipmentResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class EquipmentApi(private val httpClient: HttpClient) {

    suspend fun getMantenimientos(): List<EquipmentMantListResponse> {
        return httpClient.get("mantenimientos").body()
    }

    suspend fun getMantenimientosKMP(): List<EquipmentResponse> {
        return httpClient.get("equipment/api/getequipment").body()
    }

}
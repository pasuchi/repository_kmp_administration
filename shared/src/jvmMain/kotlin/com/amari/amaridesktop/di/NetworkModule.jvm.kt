package com.amari.amaridesktop.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val jvmNetworkModulde = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true; isLenient = true })
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "mi-api-drive-470163071363.us-central1.run.app"
                    // port = 8080            // si usas Puerto distinto
                    // path("api/")           // prefijo común opcional
                }
                contentType(ContentType.Application.Json)
            }
        }
    }
}
package com.amari.amaridesktop.desktop.data.wrapper

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import kotlin.coroutines.cancellation.CancellationException

class ApiClient(@PublishedApi internal val client: HttpClient) {

    suspend inline fun <reified T : Any> request(
        method: HttpMethod,
        url: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> = try {
        val response: HttpResponse = client.request(url) {
            this.method = method
            block()
        }

        when (response.status) {
            HttpStatusCode.OK -> Result.success(response.body<T>())
            HttpStatusCode.Unauthorized -> Result.failure(AppException.Unauthorized())
            else -> Result.failure(AppException.Server(response.status.value))
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(AppException.Network(e))
    }
}
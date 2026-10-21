package com.amari.amaridesktop.desktop.data.wrapper

sealed class AppException(message: String) : Exception(message) {
    class Unauthorized : AppException("Credenciales inválidas")
    class Server(code: Int) : AppException("Error del servidor ($code)")
    class Network(cause: Throwable) : AppException(cause.message ?: "Error de red")
}
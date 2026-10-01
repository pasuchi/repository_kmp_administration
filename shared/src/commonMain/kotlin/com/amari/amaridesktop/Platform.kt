package com.amari.amaridesktop

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
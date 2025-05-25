package com.amary.disney.character.disneychar.data.di

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual fun httpClientEngine(): HttpClientEngine {
    return OkHttp.create()
}

actual fun getHostName(): String {
    return try {
        java.net.InetAddress.getLocalHost().hostName
    } catch (_: Exception) {
        "android-device"
    }
}
package com.amary.disney.character.disneychar.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

actual fun httpClient(enableNetworkLogs: Boolean): HttpClient = createHttpClient(enableNetworkLogs) {
    engine {
        connectTimeout = 100_000
        socketTimeout = 100_000
    }
}

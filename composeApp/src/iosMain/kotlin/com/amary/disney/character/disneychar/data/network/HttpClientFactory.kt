package com.amary.disney.character.disneychar.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun httpClient(enableNetworkLogs: Boolean): HttpClient = createHttpClient(enableNetworkLogs) {
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}

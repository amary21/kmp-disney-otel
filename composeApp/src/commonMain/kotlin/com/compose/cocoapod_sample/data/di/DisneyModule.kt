package com.compose.cocoapod_sample.data.di

import com.compose.cocoapod_sample.data.api.repository.DisneyRepository
import com.compose.cocoapod_sample.data.implementation.remote.api.DisneyApi
import com.compose.cocoapod_sample.data.implementation.remote.api.DisneyApiImpl
import com.compose.cocoapod_sample.data.implementation.repository.DisneyRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import co.touchlab.kermit.Logger as KermitLogger


expect fun httpClientEngine(): HttpClientEngine

val disneyModule = module {
    single<HttpClient> {
        HttpClient(httpClientEngine()) {
            expectSuccess = true
            install(HttpTimeout) {
                socketTimeoutMillis = 60_000
                requestTimeoutMillis = 60_000
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        KermitLogger.d("KtorClient"){ message }
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }
            defaultRequest {
                url("https://api.disneyapi.dev/")
                contentType(ContentType.Application.Json)
            }
        }
    }
    // Provide DisneyApi implementation
    single<DisneyApi> { 
        DisneyApiImpl(
            httpClient = get<HttpClient>(),
            ioDispatcher = Dispatchers.IO
        ) 
    }

    // Provide DisneyRepository implementation
    single<DisneyRepository> { 
        DisneyRepositoryImpl(
            disneyApi = get<DisneyApi>(),
            ioDispatcher = Dispatchers.IO
        ) 
    }
}

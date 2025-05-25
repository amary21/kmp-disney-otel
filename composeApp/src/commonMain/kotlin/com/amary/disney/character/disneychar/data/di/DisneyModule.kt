package com.amary.disney.character.disneychar.data.di

import com.amary.disney.character.disneychar.data.api.otel.OpenTelemetryService
import com.amary.disney.character.disneychar.data.api.repository.DisneyRepository
import com.amary.disney.character.disneychar.data.implementation.remote.api.DisneyApi
import com.amary.disney.character.disneychar.data.implementation.remote.api.DisneyApiImpl
import com.amary.disney.character.disneychar.data.implementation.repository.DisneyRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.statement.request
import io.ktor.http.encodedPath


expect fun httpClientEngine(): HttpClientEngine

expect fun getHostName(): String

fun disneyModule(openTelemetryService: OpenTelemetryService) = module {
    // Initialize OpenTelemetry service
    openTelemetryService.build(
        httpEndpoint = "https://otlp.hinha.web.id",
        authorization = "$2a$04\$MslvP7qnyS8DThjgAYoexOs8.SP5/9TJ19ywVCk.1sXHjJUajEmG.",
        serviceName = "disney-character",
        hostName = getHostName()
    )
    openTelemetryService.trace("disney-app-mobile", "1.0.0")

    // Register the already initialized service
    single<OpenTelemetryService> { openTelemetryService }
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
            install(interceptorPlugin(get()))
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

private fun interceptorPlugin(
    openTelemetryService: OpenTelemetryService
) = createClientPlugin("CustomInterceptors") {
    onRequest { request, _ ->
        val networkQualifier = request.url.host + request.url.encodedPath
        openTelemetryService.createSpan(
            spanName = "Network Request",
            eventName = "Request to $networkQualifier",
            attributes = mapOf("url" to request.url.toString())
        )
        request.headers.append("X-Request-ID", networkQualifier)
    }
    onResponse {
        val networkQualifier = it.request.url.host + it.request.url.encodedPath
        openTelemetryService.createSpan(
            spanName = "Network Response",
            eventName = "Response from $networkQualifier",
            attributes = mapOf(
                "status" to it.status.value.toString(),
                "data" to it.body<String>()
            )
        )
    }
}

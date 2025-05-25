package com.amary.disney.character.disneychar.data.api.otel

interface OpenTelemetryService {
    fun build(
        httpEndpoint: String,
        authorization: String,
        serviceName: String,
        hostName: String
    )

    fun trace(
        instrumentationName: String,
        instrumentationVersion: String
    )
    fun createSpan(
        spanName: String,
        eventName: String = "",
        attributes: Map<String, String> = emptyMap(),
    )
}

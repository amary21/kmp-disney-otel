package com.amary.disney.character.disneychar.data.implementation.otel

expect class OpenTelemetryUtil {
    fun build(
        httpEndpoint: String,
        serviceName: String,
        hostName: String
    )
}
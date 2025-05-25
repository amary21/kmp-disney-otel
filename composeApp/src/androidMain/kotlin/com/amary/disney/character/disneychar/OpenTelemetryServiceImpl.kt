package com.amary.disney.character.disneychar

import android.util.Log
import com.amary.disney.character.disneychar.data.api.otel.OpenTelemetryService
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.exporter.logging.LoggingSpanExporter
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import io.opentelemetry.semconv.ServiceAttributes.SERVICE_NAME
import io.opentelemetry.semconv.incubating.HostIncubatingAttributes.HOST_NAME

class OpenTelemetryServiceImpl: OpenTelemetryService {
    private var openTelemetrySdk: OpenTelemetrySdk? = null
    private var tracer: Tracer? = null

    override fun build(httpEndpoint: String, authorization: String, serviceName: String, hostName: String) {
        Log.e("build", "HELLO WORLD")

        val otelResource = Resource.getDefault().merge(
            Resource.create(
                Attributes.of(
                    SERVICE_NAME, serviceName,
                    HOST_NAME, hostName
                )
            )
        )

        val sdkTracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
            .addSpanProcessor(BatchSpanProcessor.builder(
                OtlpHttpSpanExporter.builder()
                    .setEndpoint(httpEndpoint)
                    .addHeader("Authorization", authorization)
                    .build()).build()
            )
            .setResource(otelResource)
            .build()

        openTelemetrySdk = OpenTelemetrySdk.builder()
            .setTracerProvider(sdkTracerProvider)
            .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
            .buildAndRegisterGlobal()
    }

    override fun trace(instrumentationName: String, instrumentationVersion: String) {
        tracer = openTelemetrySdk?.getTracer(
            instrumentationName,
            instrumentationVersion
        )

        Log.e("trace", tracer.toString())

        val span = tracer?.spanBuilder("First Fragment Button onClick")?.startSpan()
        try {
            span?.makeCurrent().use { scope ->
                // Obtain the trace ID.
                Log.e("trace", span?.spanContext?.traceId.toString())
                println(span?.spanContext?.traceId)
                // Obtain the span ID.

                Log.e("trace", span?.spanContext?.spanId.toString())
                println(span?.spanContext?.spanId)
            }
        } catch (t: Throwable) {
            span?.setStatus(StatusCode.ERROR, "Something wrong in onClick")
            Log.e("trace", "Error in onClick", t)
        } finally {
            span?.end()
        }
    }
}
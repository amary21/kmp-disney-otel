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
        if (openTelemetrySdk == null) {
            Log.e("trace", "OpenTelemetry SDK is not initialized. Call build() first.")
            return
        }

        tracer = openTelemetrySdk?.getTracer(
            instrumentationName,
            instrumentationVersion
        )

        Log.e("trace", "Tracer initialized: ${tracer != null}")
    }

    override fun createSpan(
        spanName: String,
        eventName: String,
        attributes: Map<String, String>
    ) {
        if (tracer == null) {
            Log.e("createSpan", "Tracer is not initialized. Call trace() first.")
            return
        }
        val span = tracer?.spanBuilder(spanName)?.startSpan()
        try {
            span?.makeCurrent().use { scope ->
                // Log span and trace IDs for debugging
                Log.d("createSpan", "Trace ID: ${span?.spanContext?.traceId}")
                Log.d("createSpan", "Span ID: ${span?.spanContext?.spanId}")
            }

            attributes.forEach { (key, value) ->
                span?.setAttribute(key, value)
            }

            span?.addEvent(eventName)
        } catch (t: Throwable) {
            span?.setStatus(StatusCode.ERROR, "Error in span: ${t.message}")
            Log.e("createSpan", "Error in span: ${spanName}", t)
        } finally {
            span?.end()
        }
    }
}

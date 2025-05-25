import composeApp
import OpenTelemetryProtocolExporterHttp
import OpenTelemetryProtocolExporterCommon
import Foundation
import OpenTelemetryApi
import OpenTelemetrySdk
import StdoutExporter

class OpenTelemetryServiceImpl: OpenTelemetryService {

    private var tracer: Tracer? = nil

    func build(httpEndpoint: String, authorization: String, serviceName: String, hostName: String) {
        let url = URL(string: httpEndpoint)
        let config = OtlpConfiguration(
            headers: [("Authorization", authorization)]
        )
        let otlpHttpTraceExporter = OtlpHttpTraceExporter(
            endpoint: url!,
            config: config
        )
        let resource = Resource(attributes: [
            ResourceAttributes.serviceName.rawValue: AttributeValue.string(serviceName),
            ResourceAttributes.hostName.rawValue: AttributeValue.string(hostName)
        ])
        let consoleTraceExporter = StdoutSpanExporter(isDebug: true)
        OpenTelemetry.registerTracerProvider(
            tracerProvider: TracerProviderBuilder()
                .add(spanProcessor: BatchSpanProcessor(spanExporter: otlpHttpTraceExporter))
                .add(spanProcessor: BatchSpanProcessor(spanExporter: consoleTraceExporter))
                .with(resource: resource)
                .build()
        )
    }

    func trace(instrumentationName: String, instrumentationVersion: String) {
        tracer = OpenTelemetry.instance.tracerProvider.get(
            instrumentationName: instrumentationName,
            instrumentationVersion: instrumentationVersion
        )
        print("Tracer initialized: \(tracer != nil)")
    }

    func createSpan(spanName: String, eventName: String, attributes: [String: String] = [:]) {
        guard let tracer = tracer else {
            print("Tracer is not initialized. Call trace() first.")
            return
        }
        
        let span = tracer.spanBuilder(spanName: spanName).startSpan()
        
        print("Trace ID: \(span.context.traceId.hexString)")
        print("Span ID: \(span.context.spanId.hexString)")
        
        attributes.forEach { (key: String, value: String) in
            span.setAttribute(key: key, value: value)
        }
        span.addEvent(name: eventName)
        
        span.end()
    }
}

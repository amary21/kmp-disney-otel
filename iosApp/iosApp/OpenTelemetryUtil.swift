import Foundation
import OpenTelemetryProtocolExporterHttp
import OpenTelemetryApi
import OpenTelemetrySdk
import StdoutExporter

@objc public class OpenTelemetryUtil: NSObject {

    @objc public static func initBuild(
        httpEndpoint: String,
        serviceName: String,
        hostName: String
    ) {
        let url = URL(string: httpEndpoint)
        let otlpHttpTraceExporter = OtlpHttpTraceExporter(endpoint: url!)
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
    
    @objc public static func getTracer(instrumentationName: String, instrumentationVersion: String) -> Any {
        return OpenTelemetry.instance.tracerProvider.get(
            instrumentationName: instrumentationName,
            instrumentationVersion: instrumentationVersion
        )
    }

}

package com.example.order.config

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.semconv.ResourceAttributes
import org.slf4j.MDC
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.WebFilter

@Configuration
class TracingConfiguration {

//    @Bean
//    @ConditionalOnProperty(name = ["otel.enabled"], havingValue = "true")
//    fun openTelemetry(): OpenTelemetry? {
//        // Set up resource attributes
//        val resource: Resource = Resource.getDefault()
//                .toBuilder()
//                .put(ResourceAttributes.SERVICE_NAME, "spring-boot-app")
//                .build()
//
//        // Set up OTLP exporter
//        val otlpExporter = OtlpGrpcSpanExporter.builder()
//                .setEndpoint("http://localhost:4317") // Replace with your OTLP endpoint
//                .build()
//
//        // Set up span processor
//        val spanProcessor = BatchSpanProcessor.builder(otlpExporter).build()
//
//        // Set up tracer provider
//        val tracerProvider = SdkTracerProvider.builder()
//                .addSpanProcessor(spanProcessor)
//                .setResource(resource)
//                .build()
//
//        // Build OpenTelemetry instance
//        return OpenTelemetrySdk.builder()
//                .setTracerProvider(tracerProvider)
//                .build()
//    }

    @Bean
    fun tracer(openTelemetry: OpenTelemetry): Tracer {
        return openTelemetry.getTracer("kafka-tracing")
    }
//
//    @Bean
//    fun tracingFilter(): WebFilter {
//        return WebFilter { exchange, chain ->
//            val path = exchange.request.uri.path
//            if (path == "/actuator/health") {
//                val span = Span.current()
//                span.makeCurrent()
//                span.end()
//                return@WebFilter chain.filter(exchange)
//            } else {
//                val currentSpan: Span = Span.current()
//                val traceId = currentSpan.spanContext.traceId
//                val spanId = currentSpan.spanContext.spanId
//                if (traceId.isNotBlank()) {
//                    MDC.put("traceId", traceId)
//                }
//                if (spanId.isNotBlank()) {
//                    MDC.put("spanId", spanId)
//                }
//                chain.filter(exchange)
//            }
//        }
//    }
}
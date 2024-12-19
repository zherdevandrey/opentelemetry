package com.example.order.config

import io.opentelemetry.api.common.Attributes
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.common.CompletableResultCode
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.data.SpanData
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.sdk.trace.export.SpanExporter
import io.opentelemetry.semconv.ResourceAttributes
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Configuration
class OpenTelemetryConfig {


    @Bean
    fun openTelemetrySdk(@Value("\${spring.application.name}") applicationName:String, spanExporter:SpanExporter): OpenTelemetrySdk {
        val customSampler = CustomSampler()
        val resource = Resource.create(
                Attributes.of(ResourceAttributes.SERVICE_NAME, applicationName)
        )

        val tracerProvider = SdkTracerProvider.builder()
                .setSampler(customSampler)
                .addSpanProcessor(
                        BatchSpanProcessor
                                .builder(spanExporter)
                                .build()
                )
                .addResource(resource)
                .build()

        return OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .build()
    }

    @Bean
    @Profile("otel")
    fun otelSpanExporter():SpanExporter {
        return OtlpGrpcSpanExporter.builder()
                .setEndpoint("http://localhost:4317")
                .build()
    }

    @Bean
    @Profile("!otel")
    fun noopSpanExporter():SpanExporter {
        return object :SpanExporter {
            override fun export(spans: MutableCollection<SpanData>): CompletableResultCode {
                return CompletableResultCode.ofSuccess()
            }

            override fun flush(): CompletableResultCode {
                return CompletableResultCode.ofSuccess()
            }

            override fun shutdown(): CompletableResultCode {
                return CompletableResultCode.ofSuccess()
            }
        }
    }
}
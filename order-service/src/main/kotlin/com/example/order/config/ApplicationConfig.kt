package com.example.order.config

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Tracer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ApplicationConfig {

    @Bean
    fun tracer(openTelemetry: OpenTelemetry): Tracer {
        return openTelemetry.getTracer("kafka-tracing")
    }
}
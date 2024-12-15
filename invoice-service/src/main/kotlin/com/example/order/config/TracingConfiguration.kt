package com.example.order.config

import io.opentelemetry.api.trace.Span
import org.slf4j.MDC
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.WebFilter

@Configuration
class TracingConfiguration {

    @Bean
    fun tracingFilter(): WebFilter {
        return WebFilter { exchange, chain ->
            val path = exchange.request.uri.path
            if (path == "/actuator/health") {
                val span = Span.current()
                span.makeCurrent()
                span.end()
                return@WebFilter chain.filter(exchange)
            } else {
                val currentSpan: Span = Span.current()
                val traceId = currentSpan.spanContext.traceId
                val spanId = currentSpan.spanContext.spanId
                if (traceId.isNotBlank()) {
                    MDC.put("traceId", traceId)
                }
                if (spanId.isNotBlank()) {
                    MDC.put("spanId", spanId)
                }
                chain.filter(exchange)
            }
        }
    }
}
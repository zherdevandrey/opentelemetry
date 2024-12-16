package com.example.order.scheduler

import com.example.order.repository.InvoiceRepository
import com.example.order.service.InvoiceService
import io.opentelemetry.api.trace.*
import io.opentelemetry.context.Context
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlinx.coroutines.runBlocking
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.context.Scope

@Component
@EnableScheduling
class OrderScheduler(
    private val invoiceService: InvoiceService,
    val invoiceRepository: InvoiceRepository,
    private val tracer: Tracer
) {

    private val log = LoggerFactory.getLogger(OrderScheduler::class.java)

    @Scheduled(initialDelay = 0, fixedDelayString = "5000")
    fun schedule() = runBlocking{
        println("START SCHEDULE")

        val traceId = "0123456789abcdef0123456789abcdef"
        val spanId = "abcdef1234567890"

        // Создаём SpanContext с кастомным traceId
        val spanContext = SpanContext.create(
            traceId,
            spanId,
            TraceFlags.getDefault(),
            TraceState.getDefault()
        )

        // Создаём Span с кастомным контекстом
        val customSpan = tracer.spanBuilder("CustomDatabaseQuery")
            .setParent(Context.root().with(Span.wrap(spanContext)))
            .startSpan()

        try {
            // Устанавливаем Scope с текущим контекстом
            Scope { customSpan.makeCurrent() }.use {
                log.info("Executing database query with traceId: {}", customSpan.spanContext.traceId)

                // Вызов базы данных в рамках текущего контекста
                val order = invoiceService.getOrder(1L)
                println("ORDER " + order)
            }
        } finally {
            customSpan.end()
        }

        println("END SCHEDULE")
    }

}
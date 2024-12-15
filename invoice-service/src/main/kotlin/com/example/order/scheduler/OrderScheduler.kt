package com.example.order.scheduler

import com.example.order.service.InvoiceService
import com.example.order.withMDC
import io.opentelemetry.api.trace.*
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlinx.coroutines.runBlocking

@Component
@EnableScheduling
class OrderScheduler(
    private val invoiceService: InvoiceService,
    private val tracer: Tracer
) {

    private val log = LoggerFactory.getLogger(OrderScheduler::class.java)

    @Scheduled(fixedDelayString = "1000")
    fun schedule() = runBlocking{
        val traceId = "0123456789abcdef0123456789abcdef" // 32 шестнадцатеричных символа (16 байт)
        val spanId = "abcdef1234567890"

        withMDC(tracer, "INVOICE PROCESSING", traceId, spanId, ) {
            val invoiceId = 1L
            log.info("Request order by id {}", invoiceId)
            val order = invoiceService.getOrder(invoiceId)
            log.info("Received order with id {}", order.id)
        }
    }

}
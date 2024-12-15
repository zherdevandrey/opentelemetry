package com.example.order.scheduler

import com.example.order.service.InvoiceService
import io.opentelemetry.api.trace.*
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import io.opentelemetry.context.Context
import java.util.UUID

@Component
@EnableScheduling
class OrderScheduler(
    private val invoiceService: InvoiceService,
    private val tracer: Tracer
) {

    private val log = LoggerFactory.getLogger(OrderScheduler::class.java)

    @Scheduled(fixedDelayString = "1000")
    fun schedule() {
        val myTraceId = "0123456789abcdef0123456789abcdef" // 32 шестнадцатеричных символа (16 байт)
        val mySpanId = "abcdef1234567890"

        val spanContext = SpanContext.create(
            myTraceId,
            mySpanId,
            TraceFlags.getDefault(),
            TraceState.builder().build()
        )

        val span = tracer.spanBuilder("manual-span")
            .setParent(Context.current().with(Span.wrap(spanContext)))
            .startSpan()


        MDC.put("traceId", span.spanContext.traceId)
        MDC.put("spanId", span.spanContext.spanId)

        val mdcContextMap = MDC.getCopyOfContextMap()


        val id = 1L
        log.info("Request order by invoice id {}", id)
        invoiceService.getInvoice(id)
            .subscribe {
                MDC.setContextMap(mdcContextMap)
                log.info("Received order id {} by invoice id {}", it.id, id)
            }

        span.end()
    }

}
package com.example.order

import com.example.order.entity.InvoiceEntity
import com.example.order.repository.InvoiceRepository
import com.example.order.service.InvoiceService
import io.opentelemetry.api.trace.*
import io.opentelemetry.context.Context
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootApplication
class InvoiceApplication(val invoiceService: InvoiceService, val tracer: Tracer) : CommandLineRunner {
    override fun run(vararg args: String?) = runBlocking {


        println("START1")

        val customTraceId = "429878d71f10d778d5074090b2b95841"
        val customSpanId = "adc1f92e13b68ac2"
        val spanContext = SpanContext.create(
            customTraceId, customSpanId, TraceFlags.getSampled(), TraceState.getDefault()
        )
        val span = tracer.spanBuilder("DatabaseCall").setParent(Context.root().with(Span.wrap(spanContext))).startSpan()

        try {
            val context = Context.current().with(span)
            context.makeCurrent().use {
                println("Current Trace ID: ${Span.current().spanContext.traceId}")
                println("Current Span ID: ${Span.current().spanContext.spanId}")

                invoiceService.getOrder(1L)

            }
        } finally {
            span.end()
        }

        println("END1")

//
//        println("START2")
//        val spanContext2 = SpanContext.create(
//            "0123456789abcdef0123456789abcdef",
//            "abcdef1234567890",
//            TraceFlags.getDefault(),
//            TraceState.getDefault()
//        )
//
//        val customSpan = tracer.spanBuilder("CustomDatabaseQuery")
//            .setParent(Context.root().with(Span.wrap(spanContext2)))
//            .startSpan()
//
//        try {
//            Scope { customSpan.makeCurrent() }.use {
//                runBlocking {
//                    invoiceRepository.save(
//                        InvoiceEntity(1L, 1L, LocalDate.now(), BigDecimal.valueOf(1L))
//                    )
//                }
//            }
//        } finally {
//            customSpan.end()
//        }
//        println("END2")
//
//
//        println("START3")
//        runBlocking {
//            invoiceRepository.save(
//                InvoiceEntity(1L, 1L, LocalDate.now(), BigDecimal.valueOf(1L))
//            )
//        }
//        println("END3")
    }
}

fun main(args: Array<String>) {
    runApplication<InvoiceApplication>(*args)
}
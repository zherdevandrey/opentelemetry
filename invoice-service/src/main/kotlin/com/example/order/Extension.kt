package com.example.order

import io.opentelemetry.api.trace.*
import io.opentelemetry.context.Context
import kotlinx.coroutines.withContext
import org.slf4j.MDC

const val TRACE_ID = "traceId"
const val SPAN_ID = "spanId"

suspend fun <T> withMDC(block: suspend () -> T): T {
    return withContext(MDCCoroutineContext(MDC.getCopyOfContextMap())) {
        try {
            block()
        }finally {
            MDC.clear()
        }
    }
}

suspend fun <T> withMDC(tracer: Tracer, spanName:String, traceId:String, spanId:String, block: suspend () -> T): T {
    val spanContext = SpanContext.create(
        traceId,
        spanId,
        TraceFlags.getDefault(),
        TraceState.builder().build()
    )

    val span = tracer.spanBuilder(spanName)
        .setParent(Context.current().with(Span.wrap(spanContext)))
        .startSpan()

    return withContext(MDCCoroutineContext(MDC.getCopyOfContextMap())) {
        try {
            block()
        }finally {
            span.end()
        }
    }
}


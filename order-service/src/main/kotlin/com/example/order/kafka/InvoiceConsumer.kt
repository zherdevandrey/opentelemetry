package com.example.order.kafka

import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Context
import io.opentelemetry.context.propagation.TextMapGetter
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.header.Headers
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class InvoiceConsumer(val tracer: Tracer) {

    private val log = LoggerFactory.getLogger(InvoiceConsumer::class.java)

//    @KafkaListener(
//        topics = ["order-request-topic"], groupId = "order-request-topic-consumer"
//    )
//    fun consumeWithSpan(record: ConsumerRecord<String, String>) {
//        // Извлекаем контекст трассировки из заголовков
//        val extractedContext = GlobalOpenTelemetry.getPropagators().textMapPropagator.extract(
//                Context.current(),
//                record.headers(),
//                KafkaHeaderGetter()
//            )
//
//        // Входим в извлеченный контекст и начинаем новый спан
//        val span = tracer.spanBuilder("kafka-receive").setParent(extractedContext).startSpan()
//        try {
//            log.info("Received message: ${record.value()}")
//        } finally {
//            span.end()
//        }
//    }

    @KafkaListener(
        topics = ["order-request-topic"], groupId = "order-request-topic-consumer"
    )
    fun consume(payload:String) {
        log.info("Received message: {}", payload)
    }

}

class KafkaHeaderGetter : TextMapGetter<Headers> {
    override fun keys(carrier: Headers): MutableIterable<String> {
        return carrier.map { it.key() }.toMutableList()
    }

    override fun get(carrier: Headers?, key: String): String? {
        return carrier?.lastHeader(key)?.value()?.toString(Charsets.UTF_8)
    }

}
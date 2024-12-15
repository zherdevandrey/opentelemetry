package com.example.order.service

import com.example.order.dto.InvoiceDto
import com.example.order.dto.OrderDto
import com.example.order.repository.InvoiceRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Context
import io.opentelemetry.context.propagation.TextMapSetter
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.Headers
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.math.BigDecimal.valueOf
import java.time.LocalDate


@Service
class InvoiceService(
    val invoiceRepository: InvoiceRepository,
    val webClient: WebClient,
    val kafkaTemplate: KafkaTemplate<String, String>,
    val tracer: Tracer
) {

    fun getInvoice(id: Long): Mono<InvoiceDto> {
        val invoiceEntity = invoiceRepository.findById(id).orElseThrow()
        return webClient.get().uri("http://localhost:8082/orders/${invoiceEntity.orderId}")
            .exchangeToMono {
                it.bodyToMono(OrderDto::class.java)
                    .map { orderDto ->
                        InvoiceDto(
                            invoiceEntity.id!!,
                            invoiceEntity.orderDate!!,
                            invoiceEntity.totalAmount!!,
                            orderDto
                        )
                    }
            }.doOnError {
                throw RuntimeException("Order with id ${invoiceEntity.orderId} was not found")
            }
    }

    fun createInvoice(id: Long) {
        val invoiceDto = InvoiceDto(id, LocalDate.now(), valueOf(id * 10))
        val objectMapper = jacksonObjectMapper()
        objectMapper.findAndRegisterModules()
        val payload = objectMapper.writeValueAsString(invoiceDto)
        val topic = "order-request-topic"

        val span = tracer.spanBuilder("kafka-send").startSpan()
        val context = Context.current().with(span)
        val record = ProducerRecord<String, String>("order-request-topic", payload).apply {
            GlobalOpenTelemetry.getPropagators().textMapPropagator.inject(context, headers(), KafkaHeaderSetter())
        }
//        kafkaTemplate.send(record)
        kafkaTemplate.send(topic, payload)
    }
}

class KafkaHeaderSetter : TextMapSetter<Headers> {
    override fun set(headers: Headers?, key: String, value: String) {
        value.let { headers!!.add(key, it.toByteArray()) }
    }
}
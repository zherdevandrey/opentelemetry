package com.example.order.api

import com.example.order.dto.InvoiceDto
import com.example.order.service.InvoiceService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/invoices")
class InvoiceController(val invoiceService: InvoiceService) {

    private val log = LoggerFactory.getLogger(InvoiceController::class.java)

    @GetMapping(value = ["/get/{id}"])
    fun getInvoice(@PathVariable id: Long): Mono<InvoiceDto> {
        log.info("InvoiceController getInvoice request. ${id}")
        return invoiceService.getInvoice(id)
    }

    @GetMapping(value = ["/create/{id}"])
    fun createInvoice(@PathVariable id: Long) {
        log.info("InvoiceController createInvoice request. ${id}")
        invoiceService.createInvoice(id)
    }
}
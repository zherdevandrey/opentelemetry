package com.example.order

import com.example.order.entity.InvoiceEntity
import com.example.order.repository.InvoiceRepository
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootApplication
class InvoiceApplication(val invoiceRepository: InvoiceRepository):CommandLineRunner {
    override fun run(vararg args: String?) = runBlocking{
        val intRange = 1..10
        intRange.forEach{
            invoiceRepository.save(
                InvoiceEntity(it.toLong(), it.toLong(), LocalDate.now(), BigDecimal.valueOf(it.toLong()))
            )
        }
    }
}

fun main(args: Array<String>) {
    runApplication<InvoiceApplication>(*args)
}
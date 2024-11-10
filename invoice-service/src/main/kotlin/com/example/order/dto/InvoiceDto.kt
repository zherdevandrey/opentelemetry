package com.example.order.dto

import java.math.BigDecimal
import java.time.LocalDate

data class InvoiceDto(
        val id: Long,
        val orderDate: LocalDate,
        val totalAmount: BigDecimal,
        val orderDto: OrderDto? = null
)

package com.example.order.dto

import java.time.LocalDate

data class OrderDto(
        val id: Long,
        val orderDate: LocalDate,
        val count: Int
)

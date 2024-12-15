package com.example.order.repository

import com.example.order.entity.InvoiceEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface InvoiceRepository : CoroutineCrudRepository<InvoiceEntity, Long> {
}
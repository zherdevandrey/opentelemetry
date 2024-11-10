package com.example.order.repository

import com.example.order.entity.InvoiceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InvoiceRepository : JpaRepository<InvoiceEntity, Long> {
}
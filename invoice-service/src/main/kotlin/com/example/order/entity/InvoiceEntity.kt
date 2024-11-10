package com.example.order.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "INVOICE_TABLE")
class InvoiceEntity {
    @Id
    var id: Long? = null
    var orderId: Long? = null
    var orderDate: LocalDate? = null
    var totalAmount: BigDecimal? = null

    constructor(id: Long?, orderId: Long?, orderDate: LocalDate?, totalAmount: BigDecimal?) {
        this.id = id
        this.orderId = orderId
        this.orderDate = orderDate
        this.totalAmount = totalAmount
    }
}

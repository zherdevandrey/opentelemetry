package com.example.order.entity


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDate

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

package com.example.order.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "ORDER_TABLE")
class OrderEntity {
    @Id
    var id: Long? = null
    var orderDate: LocalDate? = null
    var count: Int? = null

    constructor(id: Long?, orderDate: LocalDate?, count: Int?) {
        this.id = id
        this.orderDate = orderDate
        this.count = count
    }
}

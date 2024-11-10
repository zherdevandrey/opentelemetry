package com.example.order.api

import com.example.order.dto.OrderDto
import com.example.order.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(val orderService: OrderService) {

    private val log = LoggerFactory.getLogger(OrderController::class.java)

    @GetMapping(value = ["/{id}"])
    fun getOrder(@PathVariable id: Long): OrderDto {
        log.info("OrderController request. ${id}")
        val order = orderService.findOrder(id)
        return OrderDto(order.id!!, order.orderDate!!, order.count!!)
    }
}
package com.example.order.service

import com.example.order.entity.OrderEntity
import com.example.order.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderService(val orderRepository: OrderRepository) {

    private val log = LoggerFactory.getLogger(OrderService::class.java)

    fun findOrder(id: Long): OrderEntity {
        log.info("Find order with id {} in database", id)
        return orderRepository.findById(id).orElseThrow()
    }
}
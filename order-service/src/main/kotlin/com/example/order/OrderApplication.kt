package com.example.order

import com.example.order.entity.OrderEntity
import com.example.order.repository.OrderRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDate

@SpringBootApplication
class OrderApplication(val orderRepository: OrderRepository):CommandLineRunner {
    override fun run(vararg args: String?) {
        val intRange = 1..10
        intRange.forEach{
            orderRepository.save(OrderEntity(it.toLong(), LocalDate.now(),it * 10))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<OrderApplication>(*args)
}
package com.controllers

import com.models.OrderProduct
import com.models.OrderRequest
import com.models.OrderResponse
import com.repository.OrderRepository
import com.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction

class OrderController(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
) {

    fun processOrder(orderRequest: OrderRequest): OrderResponse = transaction {
        // Resolve the user ID using the email provided in the order request
        val user = userRepository.findUserByEmail(orderRequest.email)
        return@transaction if (user != null) {
            // If user is found, proceed to save the order with the user ID and product details
            orderRepository.saveOrder(user.id, orderRequest.products.map {
                OrderProduct(it.productId, it.quantity)
            })
        } else {
            // If user is not found, return an error response
            OrderResponse(success = false, orderId = null, message = "User with email ${orderRequest.email} not found.")
        }
    }
}

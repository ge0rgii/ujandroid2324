package com.repository

import com.database.OrdersProducts
import com.models.OrderProduct
import com.models.OrderResponse
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class OrderRepositoryImpl : OrderRepository {
    override fun saveOrder(userId: Int, products: List<OrderProduct>): OrderResponse = transaction {
        // Insert the order into the Orders table
        val orderId = Orders.insert {
            it[Orders.userId] = userId
        } get Orders.id

        // Insert each product in the order into the OrdersProducts join table
        products.forEach { product ->
            OrdersProducts.insert {
                it[OrdersProducts.orderId] = orderId
                it[OrdersProducts.productId] = product.productId
                it[OrdersProducts.quantity] = product.quantity
            }
        }


        // Return an OrderResponse indicating success
        OrderResponse(success = true, orderId = orderId)
    }
}

package com.repository

import com.models.OrderProduct
import com.models.OrderResponse

interface OrderRepository {
    fun saveOrder(userId: Int, products: List<OrderProduct>): OrderResponse
}

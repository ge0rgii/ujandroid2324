package com.database

import org.jetbrains.exposed.sql.Table

object OrdersProducts : Table() {
    val orderId = integer("orderId").references(Orders.id)
    val productId = integer("productId").references(Products.id)
    val quantity = integer("quantity")
}

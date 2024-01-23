package com.controllers

import com.database.Products
import com.models.Product

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.ResultRow

class ProductController {
    fun getProduct(id: Int): Product? = transaction {
        Products.select { Products.id eq id }
            .map { toProduct(it) }
            .singleOrNull()
    }

    fun getAllProducts(): List<Product> = transaction {
        Products.selectAll()
            .map { toProduct(it) }
    }

    private fun toProduct(row: ResultRow): Product =
        Product(
            id = row[Products.id],
            name = row[Products.name],
            price = row[Products.price],
            info = row[Products.info]
        )
}
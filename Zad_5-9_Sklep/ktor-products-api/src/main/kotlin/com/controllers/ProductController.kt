package com.controllers

import com.database.Categories
import com.database.Products
import com.models.Product

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ProductController {
    fun getProduct(id: Int): Product? = transaction {
        (Products innerJoin Categories) // Perform a join with the Categories table
            .select { Products.id eq id }
            .map { toProduct(it) }
            .singleOrNull()
    }

    fun getAllProducts(): List<Product> = transaction {
        (Products innerJoin Categories).selectAll() // Perform a join for all products
            .map { toProduct(it) }
    }

    fun getProductsByCategoryId(categoryId: Int): List<Product> = transaction {
        (Products innerJoin Categories) // Ensure a join with Categories to access category data
            .select { Products.categoryId eq categoryId } // Filter products by categoryId
            .map { toProduct(it) } // Convert the result set into Product instances
    }

    private fun toProduct(row: ResultRow): Product =
        Product(
            id = row[Products.id],
            name = row[Products.name],
            price = row[Products.price],
            info = row[Products.info],
            imageUrl = row[Products.imageUrl],
            categoryId = row[Products.categoryId],
//            amount = row[Products.amount],
            categoryName = row[Categories.name] // Extract the category name
        )
}

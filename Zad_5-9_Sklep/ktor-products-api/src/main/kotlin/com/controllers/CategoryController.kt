package com.controllers

import com.database.Categories
import com.models.Category

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.ResultRow

class CategoryController {

    fun getCategory(id: Int): Category? = transaction {
        Categories.select { Categories.id eq id }
            .map { toCategory(it) }
            .singleOrNull()
    }

    fun getAllCategories(): List<Category> = transaction {
        Categories.selectAll()
            .map { toCategory(it) }
    }

    private fun toCategory(row: ResultRow): Category =
        Category(
            id = row[Categories.id],
            name = row[Categories.name],
            description = row[Categories.description],
            imageUrl = row[Categories.imageUrl]
        )

}
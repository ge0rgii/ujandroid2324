package com.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object Products : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val price = double("price")
    val info = varchar("info", 255).nullable()
    val imageUrl = varchar("imageUrl", 255).nullable()
    // foreign key to Categories table
    val categoryId = integer("category").references(Categories.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)
}
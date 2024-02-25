package com.database

import org.jetbrains.exposed.sql.Table

object Categories : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val description = varchar("description", 255).nullable()
    val imageUrl = varchar("imageUrl", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}
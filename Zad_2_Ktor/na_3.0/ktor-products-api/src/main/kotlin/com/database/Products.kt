package com.database

import org.jetbrains.exposed.sql.Table

object Products : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val price = double("price")
    val info = varchar("info", 255)

    override val primaryKey = PrimaryKey(id)
}
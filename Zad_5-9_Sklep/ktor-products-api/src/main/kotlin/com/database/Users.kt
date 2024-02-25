package com.database

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 255).uniqueIndex()
    val hashedPassword = varchar("hashedPassword", 255)
    val firstName = varchar("firstName", 255).nullable()
    val lastName = varchar("lastName", 255).nullable()
    val address = varchar("address", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}

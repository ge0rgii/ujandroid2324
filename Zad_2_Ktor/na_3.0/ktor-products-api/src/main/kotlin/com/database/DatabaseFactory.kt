package com.database

import com.database.Products

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {
    fun init() {
        Database.connect("jdbc:sqlite:mydb.db", driver = "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(Products)
            if (Products.selectAll().count() == 0L) {

                Products.insert {
                    it[name] = "Iphone 14"
                    it[price] = 1000.00
                    it[info] = "refurbished"
                }
                Products.insert {
                    it[name] = "Nokia 3310"
                    it[price] = 1000.00
                    it[info] = "new"
                }
            }
        }
    }
}

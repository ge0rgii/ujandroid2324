package com.simpleshop.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class ProductDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productName: String,
    val productInfo: String?,
    val productImageUrl: String?,
    val productPrice: Double?,
    val quantity: Int?,
    // id from api
    val apiId: Int?
)

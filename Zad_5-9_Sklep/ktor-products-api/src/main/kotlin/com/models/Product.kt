package com.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val info: String?,
    val imageUrl: String?,
    // foreign key to Categories table
    val categoryId: Int,
//    val amount: Int?,
    val categoryName : String
)

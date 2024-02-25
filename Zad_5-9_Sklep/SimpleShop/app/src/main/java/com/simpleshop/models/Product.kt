package com.simpleshop.models

data class Product(
    val categoryId: Int?,
    val categoryName: String?,
    val id: Int?,
    val imageUrl: String?,
    val info: String?,
    val name: String?,
    val price: Double?
)
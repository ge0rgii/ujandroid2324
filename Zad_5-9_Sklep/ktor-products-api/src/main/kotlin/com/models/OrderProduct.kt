package com.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderProduct(
    val productId: Int,
    val quantity: Int
)
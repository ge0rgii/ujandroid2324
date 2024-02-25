package com.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val success: Boolean,
    val orderId: Int?,
    val message: String? = null
)

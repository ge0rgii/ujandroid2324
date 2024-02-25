package com.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val email: String,
    val products: List<OrderProduct>
)



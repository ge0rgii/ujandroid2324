package com.simpleshop.api

data class UserRequest(
    val email: String,
    val password: String
)

data class UserLoginRequest(
    val email: String,
    val password: String
)

data class UserUpdateRequest(
    val email: String,
    val newPassword: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val oldPassword: String
)

data class OrderProduct(
    val productId: Int,
    val quantity: Int
)

// Adjusted to include user email in the order submission
data class OrderWithEmailRequest(
    val email: String,
    val products: List<OrderProduct>
)

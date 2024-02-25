package com.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val email: String,
    val hashedPassword: String,
    val firstName: String?,
    val lastName: String?,
    val address: String?,
)

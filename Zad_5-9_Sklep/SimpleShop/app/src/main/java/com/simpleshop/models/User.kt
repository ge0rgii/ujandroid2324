package com.simpleshop.models

data class User(
    val email: String,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val address: String? = null
)

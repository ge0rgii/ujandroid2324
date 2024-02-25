package com.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val description: String?,
    // for image
    val imageUrl: String?
)
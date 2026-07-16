@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)
package com.jmanrique.testappand.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingResponse? = null
)

@Serializable
data class RatingResponse(
    val rate: Double,
    val count: Int
)

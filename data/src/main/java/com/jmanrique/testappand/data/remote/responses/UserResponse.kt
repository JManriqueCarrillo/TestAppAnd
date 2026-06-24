@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)
package com.jmanrique.testappand.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val email: String,
    val username: String,
    val name: UserName,
)

@Serializable
data class UserName(
    val firstname: String,
    val lastname: String,
)

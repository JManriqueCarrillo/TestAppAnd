package com.jmanrique.testappand.data.remote

import com.jmanrique.testappand.data.remote.responses.ProductResponse
import com.jmanrique.testappand.data.remote.responses.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {
    @GET("products")
    suspend fun getProducts(): List<ProductResponse>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserResponse
}

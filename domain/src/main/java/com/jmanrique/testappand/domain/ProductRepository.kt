package com.jmanrique.testappand.domain

import arrow.core.Either
import com.jmanrique.testappand.core.Failure
import com.jmanrique.testappand.core.entities.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Either<Failure, List<Product>>
    fun getFavorites(): Flow<List<Product>>
    suspend fun addFavorite(product: Product): Either<Failure, Unit>
    suspend fun removeFavorite(product: Product): Either<Failure, Unit>
    suspend fun getUserProfile(userId: Int): Either<Failure, String>
}

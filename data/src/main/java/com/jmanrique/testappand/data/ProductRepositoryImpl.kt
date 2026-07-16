package com.jmanrique.testappand.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.jmanrique.testappand.core.Failure
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.core.entities.Rating
import com.jmanrique.testappand.data.local.ProductDao
import com.jmanrique.testappand.data.local.entities.FavoriteProductEntity
import com.jmanrique.testappand.data.remote.FakeStoreApi
import com.jmanrique.testappand.data.remote.responses.ProductResponse
import com.jmanrique.testappand.domain.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: FakeStoreApi,
    private val dao: ProductDao
) : ProductRepository {

    override suspend fun getProducts(): Either<Failure, List<Product>> {
        return try {
            val responses = api.getProducts()
            responses.map { it.toDomain() }.right()
        } catch (e: Exception) {
            Failure.analyzeException(e).left()
        }
    }

    override fun getFavorites(): Flow<List<Product>> {
        return dao.getFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addFavorite(product: Product): Either<Failure, Unit> {
        return try {
            dao.insertFavorite(product.toEntity())
            Unit.right()
        } catch (e: Exception) {
            Failure.DatabaseError(e.message, throwable = e).left()
        }
    }

    override suspend fun removeFavorite(product: Product): Either<Failure, Unit> {
        return try {
            dao.deleteFavorite(product.toEntity())
            Unit.right()
        } catch (e: Exception) {
            Failure.DatabaseError(e.message, throwable = e).left()
        }
    }

    override suspend fun getUserProfile(userId: Int): Either<Failure, String> {
        return try {
            val user = api.getUser(userId)
            "${user.name.firstname} ${user.name.lastname}".right()
        } catch (e: Exception) {
            Failure.analyzeException(e).left()
        }
    }

    private fun ProductResponse.toDomain() = Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = rating?.let { Rating(it.rate, it.count) },
        isFavorite = false
    )

    private fun FavoriteProductEntity.toDomain() = Product(
        id = id,
        title = title,
        price = price,
        description = "",
        category = "",
        image = image,
        isFavorite = true
    )

    private fun Product.toEntity() = FavoriteProductEntity(
        id = id,
        title = title,
        price = price,
        image = image
    )
}

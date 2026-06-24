package com.jmanrique.testappand.data

import arrow.core.left
import arrow.core.right
import com.jmanrique.testappand.core.Failure
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.data.local.ProductDao
import com.jmanrique.testappand.data.remote.FakeStoreApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    private val api: FakeStoreApi = mockk()
    private val dao: ProductDao = mockk()
    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setup() {
        repository = ProductRepositoryImpl(api, dao)
    }

    @Test
    fun `getProducts should return remote products when API call is successful`() = runTest {
        // Given
        val remoteProducts = listOf(Product(1, "Remote", 10.0, "", "", "", null))
        coEvery { api.getProducts() } returns remoteProducts

        // When
        val result = repository.getProducts()

        // Then
        assertEquals(remoteProducts.right(), result)
    }

    @Test
    fun `getProducts should return failure when API call fails`() = runTest {
        // Given
        val errorMessage = "API error"
        coEvery { api.getProducts() } throws Exception(errorMessage)

        // When
        val result = repository.getProducts()

        // Then
        assert(result is arrow.core.Either.Left)
        val failure = (result as arrow.core.Either.Left).value
        assert(failure is Failure.ApiError)
        assertEquals(errorMessage, (failure as Failure.ApiError).message)
    }
}

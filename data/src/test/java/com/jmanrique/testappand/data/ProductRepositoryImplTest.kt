package com.jmanrique.testappand.data

import arrow.core.right
import com.jmanrique.testappand.core.Failure
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.data.local.ProductDao
import com.jmanrique.testappand.data.remote.FakeStoreApi
import com.jmanrique.testappand.data.remote.responses.ProductResponse
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
    fun `getProducts should return mapped products when API call is successful`() = runTest {
        // Given
        val remoteResponses = listOf(
            ProductResponse(1, "Remote", 10.0, "Desc", "Cat", "image", null)
        )
        val expectedProducts = listOf(
            Product(1, "Remote", 10.0, "Desc", "Cat", "image", null, false)
        )
        coEvery { api.getProducts() } returns remoteResponses

        // When
        val result = repository.getProducts()

        // Then
        assertEquals(expectedProducts.right(), result)
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

package com.jmanrique.testappand.domain.usecases

import arrow.core.left
import arrow.core.right
import com.jmanrique.testappand.core.Failure
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.domain.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductsUseCaseTest {

    private val repository: ProductRepository = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getProductsUseCase = GetProductsUseCase(repository, testDispatcher)
    }

    @Test
    fun `when execute is called, it should return products from repository`() = runTest {
        // Given
        val products = listOf(
            Product(1, "Product 1", 10.0, "Description", "Category", "image", null)
        )
        coEvery { repository.getProducts() } returns products.right()

        // When
        val result = getProductsUseCase.execute()

        // Then
        assertEquals(products.right(), result)
    }

    @Test
    fun `when repository returns failure, execute should return the same failure`() = runTest {
        // Given
        val failure = Failure.NetworkConnection
        coEvery { repository.getProducts() } returns failure.left()

        // When
        val result = getProductsUseCase.execute()

        // Then
        assertEquals(failure.left(), result)
    }
}

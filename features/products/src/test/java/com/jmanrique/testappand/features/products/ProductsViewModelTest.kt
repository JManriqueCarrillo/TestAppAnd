package com.jmanrique.testappand.features.products

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.jmanrique.testappand.core.Failure
import com.jmanrique.testappand.core.UiState
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.domain.ProductRepository
import com.jmanrique.testappand.domain.usecases.GetFavoritesUseCase
import com.jmanrique.testappand.domain.usecases.GetProductsUseCase
import com.jmanrique.testappand.domain.usecases.ToggleFavoriteUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    private val repository: ProductRepository = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private lateinit var viewModel: ProductsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getProductsUseCase = GetProductsUseCase(repository, testDispatcher)
        getFavoritesUseCase = GetFavoritesUseCase(repository, testDispatcher)
        toggleFavoriteUseCase = ToggleFavoriteUseCase(repository, testDispatcher)
        
        coEvery { repository.getProducts() } returns emptyList<Product>().right()
        coEvery { repository.getFavorites() } returns flowOf(emptyList())
    }

    @Test
    fun `init should load products and combine with favorites`() = runTest {
        // Given
        val products = listOf(Product(1, "Title", 10.0, "", "", "", null))
        val favorites = listOf(Product(1, "Title", 10.0, "", "", "", null, true))
        
        coEvery { repository.getProducts() } returns products.right()
        coEvery { repository.getFavorites() } returns flowOf(favorites)

        // When
        viewModel = ProductsViewModel(getProductsUseCase, getFavoritesUseCase, toggleFavoriteUseCase)

        // Then
        viewModel.productsState.test {
            val state = awaitItem()
            assert(state is UiState.Success)
            val resultProducts = (state as UiState.Success).data
            assertEquals(1, resultProducts.size)
            assert(resultProducts[0].isFavorite)
        }
    }

    @Test
    fun `when load products fails, state should be error`() = runTest {
        // Given
        val failure = Failure.ApiError("Network Error")
        coEvery { repository.getProducts() } returns failure.left()

        // When
        viewModel = ProductsViewModel(getProductsUseCase, getFavoritesUseCase, toggleFavoriteUseCase)

        // Then
        viewModel.productsState.test {
            val state = awaitItem()
            assert(state is UiState.Error)
            assertEquals("Network Error", (state as UiState.Error).message)
        }
    }
}

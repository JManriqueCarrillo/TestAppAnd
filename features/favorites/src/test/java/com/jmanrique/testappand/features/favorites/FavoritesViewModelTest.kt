package com.jmanrique.testappand.features.favorites

import app.cash.turbine.test
import com.jmanrique.testappand.core.UiState
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.domain.usecases.GetFavoritesUseCase
import com.jmanrique.testappand.domain.usecases.RemoveFavoriteUseCase
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
class FavoritesViewModelTest {

    private val repository: com.jmanrique.testappand.domain.ProductRepository = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()
    
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase
    private val removeFavoriteUseCase: RemoveFavoriteUseCase = mockk()

    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getFavoritesUseCase = GetFavoritesUseCase(repository, testDispatcher)
    }

    @Test
    fun `init should observe favorites`() = runTest {
        // Given
        val favorites = listOf(Product(1, "Fav", 5.0, "", "", "", null, true))
        coEvery { repository.getFavorites() } returns flowOf(favorites)

        // When
        viewModel = FavoritesViewModel(getFavoritesUseCase, removeFavoriteUseCase)

        // Then
        viewModel.favoritesState.test {
            val state = awaitItem()
            assert(state is UiState.Success)
            assertEquals(1, (state as UiState.Success).data.size)
            assertEquals("Fav", state.data[0].title)
        }
    }
}

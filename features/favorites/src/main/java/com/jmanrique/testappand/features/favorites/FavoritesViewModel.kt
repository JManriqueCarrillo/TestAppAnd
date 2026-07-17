package com.jmanrique.testappand.features.favorites

import androidx.lifecycle.viewModelScope
import com.jmanrique.testappand.core.BaseViewModel
import com.jmanrique.testappand.core.UiState
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.domain.usecases.GetFavoritesUseCase
import com.jmanrique.testappand.domain.usecases.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : BaseViewModel() {

    private val _favoritesState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val favoritesState: StateFlow<UiState<List<Product>>> = _favoritesState

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().onEach { favorites ->
                _favoritesState.value = UiState.Success(favorites)
            }.launchIn(viewModelScope)
        }
    }

    fun onRemoveFavorite(product: Product) {
        viewModelScope.launch {
            removeFavoriteUseCase(product)
        }
    }
}

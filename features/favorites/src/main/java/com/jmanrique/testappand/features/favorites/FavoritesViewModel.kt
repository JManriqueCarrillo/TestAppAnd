package com.jmanrique.testappand.features.favorites

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.jmanrique.testappand.core.BaseViewModel
import com.jmanrique.testappand.core.UiState
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.domain.usecases.GetFavoritesUseCase
import com.jmanrique.testappand.domain.usecases.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : BaseViewModel() {

    private val _favoritesState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val favoritesState: StateFlow<UiState<List<Product>>> = _favoritesState

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

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
            removeFavoriteUseCase(product).fold(
                ifLeft = {
                    _events.emit(UiEvent.ShowMessage(R.string.error_update_favorite))
                },
                ifRight = {}
            )
        }
    }

    sealed interface UiEvent {
        data class ShowMessage(@StringRes val messageRes: Int) : UiEvent
    }
}

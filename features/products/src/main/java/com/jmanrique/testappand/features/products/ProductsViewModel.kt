package com.jmanrique.testappand.features.products

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import com.jmanrique.testappand.core.BaseViewModel
import com.jmanrique.testappand.core.UiState
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.domain.usecases.GetFavoritesUseCase
import com.jmanrique.testappand.domain.usecases.GetProductsUseCase
import com.jmanrique.testappand.domain.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : BaseViewModel() {

    private val _productsState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val productsState: StateFlow<UiState<List<Product>>> = _productsState


    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    private val _rawProducts = MutableStateFlow<List<Product>>(emptyList())
    private val _favorites = MutableStateFlow<List<Product>>(emptyList())

    init {
        loadProducts()
        observeFavorites()
        combineProductsWithFavorites()
    }

    fun loadProducts() {
        executeUseCase(
            targetStateFlow = _productsState,
            useCaseCall = { getProductsUseCase.execute() },
            onSuccess = { products ->
                _rawProducts.value = products
            }
        )
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().onEach { favorites ->
                _favorites.value = favorites
            }.launchIn(viewModelScope)
        }
    }

    private fun combineProductsWithFavorites() {
        combine(_rawProducts, _favorites) { products, favorites ->
            val favoriteIds = favorites.map { it.id }.toSet()
            products.map { it.copy(isFavorite = favoriteIds.contains(it.id)) }
        }.onEach { updatedList ->
            if (updatedList.isNotEmpty() || _productsState.value is UiState.Success) {
                _productsState.value = UiState.Success(updatedList)
            }
        }.launchIn(viewModelScope)
    }

    fun onFavoriteClick(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(product).fold(
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

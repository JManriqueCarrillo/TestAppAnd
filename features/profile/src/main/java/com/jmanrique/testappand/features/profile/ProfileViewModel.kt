package com.jmanrique.testappand.features.profile

import androidx.lifecycle.viewModelScope
import com.jmanrique.testappand.core.BaseViewModel
import com.jmanrique.testappand.core.UiState
import com.jmanrique.testappand.domain.usecases.GetFavoritesUseCase
import com.jmanrique.testappand.domain.usecases.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : BaseViewModel() {

    companion object {
        private const val DEFAULT_USER_ID = 8
    }

    private val _profileNameState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val profileNameState: StateFlow<UiState<String>> = _profileNameState

    private val _favoritesCount = MutableStateFlow(0)
    val favoritesCount: StateFlow<Int> = _favoritesCount

    init {
        loadUserProfile()
        observeFavoritesCount()
    }

    private fun loadUserProfile() {
        executeUseCase(
            targetStateFlow = _profileNameState,
            useCaseCall = { getUserProfileUseCase.execute(DEFAULT_USER_ID) }
        )
    }

    private fun observeFavoritesCount() {
        viewModelScope.launch {
            getFavoritesUseCase(
                onResult = { favoritesFlow ->
                    favoritesFlow.onEach { favorites ->
                        _favoritesCount.value = favorites.size
                    }.launchIn(viewModelScope)
                }
            )
        }
    }
}

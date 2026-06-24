package com.jmanrique.testappand.features.favorites.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jmanrique.testappand.features.favorites.ui.FavoritesScreen

fun NavGraphBuilder.favoritesScreen() {
    composable("favorites") {
        FavoritesScreen(viewModel = hiltViewModel())
    }
}

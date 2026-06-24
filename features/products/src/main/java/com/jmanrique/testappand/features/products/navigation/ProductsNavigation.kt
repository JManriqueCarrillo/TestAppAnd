package com.jmanrique.testappand.features.products.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jmanrique.testappand.features.products.ui.ProductsScreen

fun NavGraphBuilder.productsScreen() {
    composable("products") {
        ProductsScreen(viewModel = hiltViewModel())
    }
}

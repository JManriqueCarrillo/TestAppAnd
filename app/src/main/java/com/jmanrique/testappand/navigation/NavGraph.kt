package com.jmanrique.testappand.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jmanrique.testappand.features.favorites.navigation.favoritesScreen
import com.jmanrique.testappand.features.products.navigation.productsScreen
import com.jmanrique.testappand.features.profile.navigation.profileScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Products.route,
        modifier = modifier
    ) {
        productsScreen()
        favoritesScreen()
        profileScreen()
    }
}

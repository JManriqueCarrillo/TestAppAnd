package com.jmanrique.testappand.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.jmanrique.testappand.R

sealed class Screen(
    val route: String,
    @StringRes val labelRes: Int,
    val icon: ImageVector
) {
    object Products : Screen("products", R.string.nav_products, Icons.Default.ShoppingCart)
    object Favorites : Screen("favorites", R.string.nav_favorites, Icons.Default.Favorite)
    object Profile : Screen("profile", R.string.nav_profile, Icons.Default.Person)

    companion object {
        val bottomNavItems = listOf(Products, Favorites, Profile)
    }
}

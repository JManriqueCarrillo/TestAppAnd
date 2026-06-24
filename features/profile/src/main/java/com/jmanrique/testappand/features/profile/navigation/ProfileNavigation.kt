package com.jmanrique.testappand.features.profile.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jmanrique.testappand.features.profile.ui.ProfileScreen

fun NavGraphBuilder.profileScreen() {
    composable("profile") {
        ProfileScreen(viewModel = hiltViewModel())
    }
}

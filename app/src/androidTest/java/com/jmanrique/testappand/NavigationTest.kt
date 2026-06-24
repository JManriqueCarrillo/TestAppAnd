package com.jmanrique.testappand

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun bottomNavigation_switchToFavorites() {
        composeTestRule.onNodeWithTag("navItem_favorites").performClick()
        composeTestRule.onNodeWithTag("topAppBarTitle").assertTextEquals("Favorites")
    }

    @Test
    fun bottomNavigation_switchToProfile() {
        composeTestRule.onNodeWithTag("navItem_profile").performClick()
        composeTestRule.onNodeWithTag("topAppBarTitle").assertTextEquals("User Profile")
    }
    
    @Test
    fun bottomNavigation_switchToProducts() {
        composeTestRule.onNodeWithTag("navItem_favorites").performClick()
        composeTestRule.onNodeWithTag("navItem_products").performClick()
        composeTestRule.onNodeWithTag("topAppBarTitle").assertTextEquals("Products")
    }
}

package com.example.fasihbook

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.fasihbook.ScreenAssertions.assertCurrentRouteName
import com.example.fasihbook.ScreenAssertions.onNodeWithStringId
import com.example.fasihbook.model.FakeBookDataSource
import com.example.fasihbook.ui.navigation.Screen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FasihBookAppKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            FasihBookApp(navController = navController)
        }
    }

    @Test
    fun verify_startDestiniation(){
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun test_click_navigateToDetailBookWithData(){
        composeTestRule.onNodeWithTag("BookList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeBookDataSource.dummyBook[10].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)
        composeTestRule.onNodeWithText(FakeBookDataSource.dummyBook[10].title).assertIsDisplayed()
    }

    @Test
    fun test_navigationMenu(){
        composeTestRule.onNodeWithStringId(R.string.favorite_page).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.about_page).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.home_page).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun test_FavoriteIsWork() {
        composeTestRule.onNodeWithTag("BookList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeBookDataSource.dummyBook[10].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)
        composeTestRule.onNodeWithText(FakeBookDataSource.dummyBook[10].title).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Icons.Default.FavoriteBorder")
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("FavoriteButton").performClick()
        composeTestRule.onNodeWithContentDescription("Icons.Default.Favorite").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.favorite_page).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithContentDescription("DeleteFavorite").performClick()
    }
}
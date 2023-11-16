package com.example.fasihbook.ui.navigation

sealed class Screen(val route: String) {
    object  Home: Screen("home")
    object  Favorite: Screen("favorite")
    object  About: Screen("about")
    object  DetailBook: Screen("home/{bookId}"){
        fun createRoute(bookId: Long) = "home/$bookId"
    }
}
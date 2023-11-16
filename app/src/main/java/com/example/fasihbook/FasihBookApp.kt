package com.example.fasihbook

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fasihbook.ui.navigation.NavigationItem
import com.example.fasihbook.ui.navigation.Screen
import com.example.fasihbook.ui.screen.about.AboutScreen
import com.example.fasihbook.ui.screen.detail.DetailScreen
import com.example.fasihbook.ui.screen.favorite.FavoriteScreen
import com.example.fasihbook.ui.screen.home.HomeScreen
import com.example.fasihbook.ui.theme.FasihBookTheme

@Composable
fun FasihBookApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailBook.route) {
                BottomBar(navController)
            }
        }, modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetile = { bookId ->
                    navController.navigate(Screen.DetailBook.createRoute(bookId))
                })
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(navigateToDetile = { bookId ->
                    navController.navigate(Screen.DetailBook.createRoute(bookId))
                })
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
            composable(
                route = Screen.DetailBook.route,
                arguments = listOf(navArgument("bookId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("bookId") ?: -1L
                DetailScreen(bookId = id,
                    navigateBack = { navController.navigateUp() },
                    onClickReadNow = {})
            }
        }
    }
}


@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home_page),
                icon = Icons.Default.Home,
                screen = Screen.Home,
            ), NavigationItem(
                title = stringResource(R.string.favorite_page),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite,
            ), NavigationItem(
                title = stringResource(R.string.about_page),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About,
            )
        )

        navigationItems.map {
            NavigationBarItem(
                selected = currentRoute == it.screen.route,
                onClick = {
                    navController.navigate(it.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = it.icon, contentDescription = it.title) },
                label = { Text(text = it.title) },
            )
        }

    }
}

@Preview
@Composable
fun FasihBookAppPreview() {
    FasihBookTheme {
        FasihBookApp()
    }
}
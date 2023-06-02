package com.andresestevez.soreh.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andresestevez.soreh.ui.screens.characters.detail.DetailScreen
import com.andresestevez.soreh.ui.screens.characters.favorites.FavoritesScreen
import com.andresestevez.soreh.ui.screens.characters.main.MainScreen
import com.andresestevez.soreh.ui.screens.characters.search.SearchScreen
import com.andresestevez.soreh.ui.screens.characters.tops.TopsScreen

@Composable
fun Navigation(paddingValues: PaddingValues, navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = NavCommand.Main.route
    ) {
        composable(route = NavCommand.Main.route) {
            MainScreen(paddingValues = paddingValues) { uiState ->
                navHostController.navigateSingleTopWithPopUpToStartDestination(
                    NavCommand.Detail.baseRoute.plus(
                        "/${uiState.character.id}"
                    )
                )
            }
        }

        composable(route = NavCommand.Search.route) {
            SearchScreen()
        }

        composable(route = NavCommand.Tops.route) {
            TopsScreen()
        }

        composable(route = NavCommand.Favorites.route) {
            FavoritesScreen()
        }

        composable(
            route = NavCommand.Detail.route,
            arguments = NavCommand.Detail.args
        ) {
            DetailScreen { navHostController.navigate(NavCommand.Main.baseRoute) }
        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

fun NavHostController.navigateSingleTopWithPopUpToStartDestination(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopWithPopUpToStartDestination.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

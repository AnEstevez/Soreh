package com.andresestevez.soreh.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.screens.common.detail.DetailScreen
import com.andresestevez.soreh.ui.screens.characters.favorites.FavoritesScreen
import com.andresestevez.soreh.ui.screens.characters.main.MainScreen
import com.andresestevez.soreh.ui.screens.characters.main.detail.MainDetailScreen
import com.andresestevez.soreh.ui.screens.characters.search.SearchScreen
import com.andresestevez.soreh.ui.screens.characters.tops.TopsScreen
import com.andresestevez.soreh.ui.screens.characters.tops.detail.TopsDetailScreen

@Composable
fun Navigation(appState: SorehAppState) {

    NavHost(
        navController = appState.navHostController,
        startDestination = NavCommand.Home.destination
    ) {

        // Home graph
        composable(route = NavCommand.Home.destination) {
            MainScreen(appState = appState) { currentCharacter, idList ->
                appState.navHostController.navigateSingleTopTo(
                    route = NavCommand.HomeDetail.destination.plus("/$currentCharacter/$idList")
                )
            }
        }

        composable(
            route = NavCommand.HomeDetail.destinationWithArgs,
            arguments = NavCommand.HomeDetail.arguments
        ) {
            MainDetailScreen()
        }


        // Search graph
        composable(route = NavCommand.Search.destination) {
            BackHandler(onBack = appState.onBack)
            SearchScreen(appState = appState) { characterId ->
                appState.navHostController.navigateSingleTopTo(
                    route = NavCommand.SearchDetail.destination.plus("/$characterId"),
                )
            }
        }

        composable(
            route = NavCommand.SearchDetail.destinationWithArgs,
            arguments = NavCommand.SearchDetail.arguments
        ) {
            DetailScreen()
        }


        // Tops graph
        composable(route = NavCommand.Tops.destination) {
            TopsScreen(appState = appState) { characterPosition, publisher ->
                appState.navHostController.navigateSingleTopTo(
                    route = NavCommand.TopsDetail.destination.plus("/$characterPosition/$publisher"),
                )
            }
        }

        composable(
            route = NavCommand.TopsDetail.destinationWithArgs,
            arguments = NavCommand.TopsDetail.arguments
        ) {
            TopsDetailScreen()
        }


        // Favorites graph
        composable(route = NavCommand.Favorites.destination) {
            BackHandler(onBack = appState.onBack)
            FavoritesScreen(
                appState = appState,
            ) { characterId ->
                appState.navHostController.navigateSingleTopTo(
                    route = NavCommand.FavoritesDetail.destination.plus("/$characterId"),
                )
            }
        }

        composable(
            route = NavCommand.FavoritesDetail.destinationWithArgs,
            arguments = NavCommand.FavoritesDetail.arguments
        ) {
            DetailScreen()
        }


    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }

fun NavHostController.navigateSingleTopWithPopUpTo(route: String, popUpDestination: String) =
    this.navigate(route) {
        popUpTo(
            popUpDestination
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

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


package com.andresestevez.soreh.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class NavCommand(
    val baseRoute: String, // For NavGraphBuilder.navigation(route = baseRoute)
    val arguments: List<NamedNavArgument>,
    val destination: String,
) {

    companion object {
        const val CHARACTER_ID = "characterId"
    }

    val destinationWithArgs = run {
        // baseroute/{arg1}/{arg2}...
        val argKeys = arguments.joinToString { "/{${it.name}}" }
        destination.plus(argKeys)
    }

    object Home : NavCommand(
        baseRoute = "home",
        arguments = emptyList(),
        destination = "home_main"
    )

    object HomeDetail : NavCommand(
        baseRoute = "home",
        arguments = listOf(
            navArgument(CHARACTER_ID) { type = NavType.IntType }
        ),
        destination = "home_detail"
    )

    object Search : NavCommand(
        baseRoute = "search",
        arguments = emptyList(),
        destination = "search_main"
    )

    object SearchDetail : NavCommand(
        baseRoute = "search",
        arguments = listOf(
            navArgument(CHARACTER_ID) { type = NavType.IntType }
        ),
        destination = "search_detail"
    )

    object Tops : NavCommand(
        baseRoute = "tops",
        arguments = emptyList(),
        destination = "tops_main"
    )

    object TopsDetail : NavCommand(
        baseRoute = "tops",
        arguments = listOf(
            navArgument(CHARACTER_ID) { type = NavType.IntType }
        ),
        destination = "tops_detail"
    )

    object Favorites : NavCommand(
        baseRoute = "favorites",
        arguments = emptyList(),
        destination = "favorites_main"
    )

    object FavoritesDetail : NavCommand(
        baseRoute = "favorites",
        arguments = listOf(
            navArgument(CHARACTER_ID) { type = NavType.IntType }
        ),
        destination = "favorites_detail"
    )

}

package com.andresestevez.soreh.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class NavItem(
    val baseRoute: String,
    val navArgs: List<NavArg> = emptyList()
) {
    val route = run {
        // baseroute/{arg1}/{arg2}...
        val argKeys = navArgs.joinToString { "/{${it.key}}" }
        baseRoute.plus(argKeys)
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

    object Main: NavItem("main")
    object Detail: NavItem("detail", listOf(NavArg.CharacterId))
}

enum class NavArg(val key: String, val navType: NavType<*>) {
    CharacterId("characterId", NavType.IntType)
}

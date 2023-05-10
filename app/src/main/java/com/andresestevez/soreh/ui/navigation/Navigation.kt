package com.andresestevez.soreh.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresestevez.soreh.ui.screens.detail.DetailScreen
import com.andresestevez.soreh.ui.screens.main.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavItem.Main.route
    ) {
        composable(route = NavItem.Main.route) {
            MainScreen { character ->
                navController.navigate(NavItem.Detail.baseRoute.plus("/${character.id}"))
            }
        }
        composable(
            route = NavItem.Detail.route,
            arguments = NavItem.Detail.args
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(NavArg.CharacterId.key)
            requireNotNull(id)
            DetailScreen(id) { navController.navigate(NavItem.Main.baseRoute) }
        }
    }
}


private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
){
   composable(
       route = navItem.route,
       arguments = navItem.args
   ){
       content(it)
   }
}
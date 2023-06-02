package com.andresestevez.soreh.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andresestevez.soreh.ui.screens.characters.main.NavItem
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
class SorehAppState(
    val navHostController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    val topAppState: TopAppBarState,
    val coroutineScope: CoroutineScope,
) {

    companion object {
        val BOTTOM_NAVIGATION_OPTIONS =
            listOf(NavItem.HOME, NavItem.SEARCH, NavItem.TOPS, NavItem.FAVORITES)
    }

    val scrollBehavior
        @Composable get() = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            topAppState
        )

    val currentRoute
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination?.route
            ?: ""

    val showTopAppBar: Boolean
        @Composable get() = currentRoute in BOTTOM_NAVIGATION_OPTIONS.map { it.navCommand.route }

    val showBottomNavigationBar: Boolean
        @Composable get() = currentRoute in BOTTOM_NAVIGATION_OPTIONS.map { it.navCommand.route }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberSorehAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    topAppState: TopAppBarState = rememberTopAppBarState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): SorehAppState = remember(navController, snackbarHostState, topAppState, coroutineScope) {
    SorehAppState(navController, snackbarHostState, topAppState, coroutineScope)
}
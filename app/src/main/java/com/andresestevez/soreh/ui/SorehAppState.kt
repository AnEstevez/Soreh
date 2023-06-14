package com.andresestevez.soreh.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andresestevez.soreh.ui.navigation.NavCommand
import com.andresestevez.soreh.ui.navigation.navigateSingleTopWithPopUpToStartDestination
import com.andresestevez.soreh.ui.screens.characters.main.NavItem

@OptIn(ExperimentalMaterial3Api::class)
class SorehAppState(
    val navHostController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    val homeTopAppBarState: TopAppBarState,
    val favoritesTopAppBarState: TopAppBarState,
    val bottomSheetScaffoldState: BottomSheetScaffoldState,
    val navigationBarsInsetsDp: MutableState<Dp>,
    var scaffoldPadding: MutableState<PaddingValues>,

    ) {

    companion object {
        val BOTTOM_NAVIGATION_OPTIONS =
            listOf(NavItem.HOME, NavItem.SEARCH, NavItem.TOPS, NavItem.FAVORITES)
    }

    val homeScrollBehavior
        @Composable get() = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            homeTopAppBarState
        )

    val topsScrollBehavior
        @Composable get() = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            homeTopAppBarState
        )

    val favoritesScrollBehavior
        @Composable get() = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            favoritesTopAppBarState
        )

    val currentRoute
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination?.route
            ?: ""

    val showTopAppBar: Boolean
        @Composable get() = currentRoute in listOf(
            NavCommand.Home.destination,
            NavCommand.Tops.destination,
            NavCommand.Favorites.destination
        )

    val showBottomNavigationBar: Boolean
        @Composable get() = currentRoute in BOTTOM_NAVIGATION_OPTIONS.map { it.navCommand.destination }

    @Composable
    fun getTopAppBarScrollBehavior(): TopAppBarScrollBehavior = when (currentRoute) {
        NavCommand.Home.destination -> homeScrollBehavior
        NavCommand.Tops.destination -> topsScrollBehavior
        NavCommand.Favorites.destination -> favoritesScrollBehavior
        else -> favoritesScrollBehavior
    }

    val onBack: () -> Unit
        @Composable get() = {
            navHostController.navigateSingleTopWithPopUpToStartDestination(
                NavCommand.Home.destination
            )
        }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberSorehAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    homeTopAppBarState: TopAppBarState = rememberTopAppBarState(),
    favoritesTopAppBarState: TopAppBarState = rememberTopAppBarState(),
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    ),
    navigationBarsInsetsDp: MutableState<Dp> = remember { mutableStateOf(0.dp) },
    scaffoldPadding: MutableState<PaddingValues> = remember { mutableStateOf(PaddingValues()) },
): SorehAppState =
    remember(
        navController,
        snackbarHostState,
        homeTopAppBarState,
        bottomSheetScaffoldState,
        navigationBarsInsetsDp,
        scaffoldPadding,
    ) {
        SorehAppState(
            navController,
            snackbarHostState,
            homeTopAppBarState,
            favoritesTopAppBarState,
            bottomSheetScaffoldState,
            navigationBarsInsetsDp,
            scaffoldPadding,
        )
    }
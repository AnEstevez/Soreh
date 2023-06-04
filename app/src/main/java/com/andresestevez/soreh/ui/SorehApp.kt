package com.andresestevez.soreh.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.navigation.Navigation
import com.andresestevez.soreh.ui.navigation.navigateSingleTopWithPopUpToStartDestination
import com.andresestevez.soreh.ui.screens.characters.main.SorehBottomNavigationBar
import com.andresestevez.soreh.ui.theme.Marcelus
import com.andresestevez.soreh.ui.theme.SorehTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SorehApp() {

    val appState = rememberSorehAppState()

    SorehScreen {

        Scaffold(
            topBar = {
                if (appState.showTopAppBar) {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                style = MaterialTheme.typography.headlineMedium.copy(fontFamily = Marcelus),
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                            scrolledContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                            titleContentColor = MaterialTheme.colorScheme.onBackground,
                            actionIconContentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        scrollBehavior = appState.getTopAppBarScrollBehavior()
                    )

                }
            },
            modifier = Modifier.nestedScroll(appState.getTopAppBarScrollBehavior().nestedScrollConnection),
            snackbarHost = { SnackbarHost(hostState = appState.snackbarHostState) },
            bottomBar = {
                if (appState.showBottomNavigationBar) {
                    SorehBottomNavigationBar(
                        navItems = SorehAppState.BOTTOM_NAVIGATION_OPTIONS,
                        currentRoute = appState.currentRoute,
                        onClick = { route ->
                            appState.navHostController.navigateSingleTopWithPopUpToStartDestination(
                                route
                            )
                        }
                    )
                }
            }
        ) { padding ->

            Navigation(padding, appState)

        }
    }
}


@Composable
fun SorehScreen(content: @Composable () -> Unit) {
    SorehTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}



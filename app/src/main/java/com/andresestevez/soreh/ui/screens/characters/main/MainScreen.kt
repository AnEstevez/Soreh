package com.andresestevez.soreh.ui.screens.characters.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.SorehApp
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.theme.Marcelus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    onClick: (ItemUiState) -> Unit = {},
) {

    SorehApp {

        val state by viewModel.state.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }

        val scrollBehavior =
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.headlineMedium.copy(fontFamily = Marcelus),
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        scrolledContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    scrollBehavior = scrollBehavior
                )

            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { padding ->

            CharacterListVerticalGrid(
                state = state,
                onClick = onClick,
                contentPaddingValues = padding
            )

            if (state.userMessage.isNotEmpty()) {
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(state.userMessage)
                }
                viewModel.dismissUserMessage()
            }
        }
    }

}



package com.andresestevez.soreh.ui.screens.characters.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.andresestevez.soreh.ui.SorehApp
import com.andresestevez.soreh.ui.screens.characters.ItemUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    onClick: (ItemUiState) -> Unit = {},
) {
    SorehApp {

        val state by viewModel.state.collectAsState()

        Scaffold(
            topBar = {
                MainAppBar()
            }
        ) { padding ->

            CharacterListVerticalGrid(
                modifier = Modifier.padding(padding),
                state = state,
                onClick = onClick
            )
        }
    }

}



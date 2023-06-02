package com.andresestevez.soreh.ui.screens.characters.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.andresestevez.soreh.ui.screens.common.ItemUiState

@Composable
fun MainScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onClick: (ItemUiState) -> Unit = {},
) {

    val state by viewModel.state.collectAsState()

    CharacterListVerticalGrid(
        state = state,
        onClick = onClick,
        contentPaddingValues = paddingValues
    )

}



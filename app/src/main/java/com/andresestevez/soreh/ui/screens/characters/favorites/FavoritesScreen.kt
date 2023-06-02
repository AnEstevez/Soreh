package com.andresestevez.soreh.ui.screens.characters.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.andresestevez.soreh.ui.screens.characters.main.CharacterListVerticalGrid

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onClick: (Int) -> Unit,
) {

    val state by viewModel.state.collectAsState()

    CharacterListVerticalGrid(
        state = state,
        onClick = { characterId -> onClick(characterId) },
        contentPaddingValues = paddingValues,
        columns = 2
    )

}
package com.andresestevez.soreh.ui.screens.characters.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.screens.characters.main.CharacterListVerticalGrid

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    appState: SorehAppState,
    onClick: (Int) -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.userMessage.isNotEmpty()) {
        appState.onShowUserMessage(state.userMessage)
        viewModel.dismissUserMessage()
    }

    CharacterListVerticalGrid(
        state = state,
        onClick = { characterId -> onClick(characterId) },
        contentPaddingValues = appState.scaffoldPadding.value,
        columns = 2
    )

}
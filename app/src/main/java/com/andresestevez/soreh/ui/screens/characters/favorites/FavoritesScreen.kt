package com.andresestevez.soreh.ui.screens.characters.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andresestevez.soreh.framework.analytics.LocalAnalyticsHelper
import com.andresestevez.soreh.framework.analytics.LogScreenView
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.screens.common.CharacterListVerticalGrid

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    appState: SorehAppState,
    onClick: (Int) -> Unit,
) {

    val localAnalyticsHelper = LocalAnalyticsHelper.current
    localAnalyticsHelper.LogScreenView("FavoritesScreen")

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (state.userMessage.isNotEmpty()) {
        appState.onShowUserMessage(state.userMessage)
        viewModel.dismissUserMessage()
    }

    CharacterListVerticalGrid(
        state = state,
        onClick = { characterId -> onClick(characterId) },
        contentPaddingValues = PaddingValues(
            top = appState.scaffoldPadding.value.calculateTopPadding(),
            start = 5.dp,
            end = 5.dp,
            bottom = appState.scaffoldPadding.value.calculateBottomPadding()
        ),
        columns = 2
    )

}
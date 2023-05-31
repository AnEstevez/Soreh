package com.andresestevez.soreh.ui.screens.common

import com.andresestevez.soreh.data.models.Character

data class UiState(
    val loading: Boolean = false,
    val data: List<ItemUiState> = emptyList(),
    val userMessage: String = "",
)

data class ItemUiState(val character: Character) {
    val onClick: (Int) -> Unit = {}
}
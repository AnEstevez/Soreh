package com.andresestevez.soreh.ui.screens.characters.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresestevez.soreh.domain.GetRandomCharactersListUseCase
import com.andresestevez.soreh.ui.screens.characters.ItemUiState
import com.andresestevez.soreh.ui.screens.characters.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(private val getRandomCharactersList: GetRandomCharactersListUseCase) :
    ViewModel() {


    private var _state = MutableStateFlow(UiState())

    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            getRandomCharactersList().collect { data ->
                _state.value =
                    UiState(data = data.map { character -> ItemUiState(character = character) }
                    )

            }
        }
    }

}
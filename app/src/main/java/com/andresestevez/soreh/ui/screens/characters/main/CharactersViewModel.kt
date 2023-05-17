package com.andresestevez.soreh.ui.screens.characters.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresestevez.soreh.domain.GetRandomCharactersListUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.screens.characters.ItemUiState
import com.andresestevez.soreh.ui.screens.characters.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val getRandomCharactersList: GetRandomCharactersListUseCase) :
    ViewModel() {

    var state = MutableStateFlow(UiState())
        private set

    init {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            getRandomCharactersList().collect { result ->
                state.update { currentState ->
                    result.fold({ characters ->
                        currentState.copy(
                            data = characters.map { character -> ItemUiState(character = character) },
                            loading = false
                        )
                    }) {
                        Timber.e(it)
                        currentState.copy(userMessage = it.getUserMessage())
                    }
                }
            }
        }
    }

    fun dismissUserMessage() {
        state.update { it.copy(userMessage = "") }
    }
}


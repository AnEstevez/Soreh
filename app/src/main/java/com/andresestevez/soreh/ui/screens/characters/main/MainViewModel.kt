package com.andresestevez.soreh.ui.screens.characters.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresestevez.soreh.domain.GetRandomCharactersListUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(getRandomCharactersList: GetRandomCharactersListUseCase) :
    ViewModel() {

    var state = MutableStateFlow(UiState())
        private set

    init {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            getRandomCharactersList(10).collect { result ->
                state.update { currentState ->
                    result.fold({ characters ->
                        currentState.copy(
                            data = characters.map { character -> ItemUiState(character = character) },
                            loading = false
                        )
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }
        }
    }

    fun dismissUserMessage() {
        state.update { it.copy(userMessage = "") }
    }
}


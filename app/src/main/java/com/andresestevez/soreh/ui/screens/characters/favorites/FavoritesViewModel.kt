package com.andresestevez.soreh.ui.screens.characters.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresestevez.soreh.domain.GetFavoritesUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(useCase: GetFavoritesUseCase) : ViewModel() {

    var state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
        private set

    init {
        viewModelScope.launch {
            state.update { currentState -> currentState.copy(loading = true) }
            useCase().collect { result ->
                state.update { currentState ->

                    result.fold({ characters ->
                        currentState.copy(
                            loading = false,
                            data = characters.map { ItemUiState(it) })
                    }) {
                        currentState.copy(
                            loading = false,
                            userMessage = it.getUserMessage()
                        )
                    }

                }
            }
        }
    }

    fun dismissUserMessage() {
        state.update { it.copy(userMessage = "") }
    }
}
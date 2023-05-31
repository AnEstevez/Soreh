package com.andresestevez.soreh.ui.screens.characters.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresestevez.soreh.domain.GetCharacterByIdUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.navigation.NavArg
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    useCase: GetCharacterByIdUseCase,
) : ViewModel() {

    var state = MutableStateFlow(UiState())
        private set

    private val characterId = stateHandle.get<Int>(NavArg.CharacterId.key) ?: 0

    init {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            useCase(characterId).collect { result ->
                state.update { currentState ->
                    result.fold({
                        currentState.copy(loading = false, data = ItemUiState(it))
                    }) {
                        currentState.copy(userMessage = it.getUserMessage())
                    }
                }
            }
        }
    }

    fun dismissUserMessage() {
        state.update { it.copy(userMessage = "") }
    }

    data class UiState(
        val loading: Boolean = false,
        val data: ItemUiState? = null,
        val userMessage: String = "",
    )
}



package com.andresestevez.soreh.ui.screens.common.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresestevez.soreh.domain.GetCharacterByIdUseCase
import com.andresestevez.soreh.domain.ToggleFavoriteUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.navigation.NavCommand.Companion.CHARACTER_ID
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    getCharacterByIdUseCase: GetCharacterByIdUseCase,
    toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val characterId = stateHandle.get<Int>(CHARACTER_ID) ?: 0

    init {
        viewModelScope.launch {
            _uiState.value = UiState(loading = true)
            getCharacterByIdUseCase(characterId).collect { result ->
                _uiState.update { currentState ->
                    result.fold({
                        currentState.copy(loading = false, data = ItemUiState(it).apply {
                            onClick = { toggleFavoriteUseCase(it) }
                        })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }
        }
    }

    fun dismissUserMessage() {
        _uiState.update { it.copy(userMessage = "") }
    }

    data class UiState(
        val loading: Boolean = false,
        val data: ItemUiState? = null,
        val userMessage: String = "",
    )
}



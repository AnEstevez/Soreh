package com.andresestevez.soreh.ui.screens.characters.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresestevez.soreh.domain.GetFavoritesUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(useCase: GetFavoritesUseCase) : ViewModel() {

    private var _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(loading = true) }
            useCase().collect { result ->
                _uiState.update { currentState ->
                    result.fold({ characters ->
                        currentState.copy(
                            loading = false,
                            data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
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
        _uiState.update { it.copy(userMessage = "") }
    }
}
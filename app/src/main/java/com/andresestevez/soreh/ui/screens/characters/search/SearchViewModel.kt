package com.andresestevez.soreh.ui.screens.characters.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresestevez.soreh.domain.SearchCharactersUseCase
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
class SearchViewModel @Inject constructor(
    private val searchCharactersUseCase: SearchCharactersUseCase,
) : ViewModel() {

    var filters = MutableStateFlow(CharactersFilter())

    private var _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var _targetCharactersState = MutableStateFlow(TargetCharactersUiState())
    val targetCharactersUiState = _targetCharactersState.asStateFlow()

    fun searchCharacters(query: String) {
        viewModelScope.launch {
            _uiState.value = UiState(loading = true)
            searchCharactersUseCase.searchCharacters(query).fold({ characters ->
                _uiState.update { currentState ->
                    currentState.copy(
                        loading = false,
                        data = characters.map { character -> ItemUiState(character) })
                }
                _targetCharactersState.update { currentState ->
                    currentState.copy(
                        loading = false,
                        data = characters.size,
                    )
                }
            }) {
                Timber.e(it)
                _uiState.update { currentState ->
                    currentState.copy(loading = false, userMessage = it.getUserMessage())
                }
            }
        }
    }

    fun countTargetCharacters(query: String) {
        viewModelScope.launch {
            _targetCharactersState.value = TargetCharactersUiState(loading = true)
            searchCharactersUseCase.countCharacters(query).fold({ count ->
                _targetCharactersState.update { currentState ->
                    currentState.copy(
                        loading = false,
                        data = count,
                    )
                }
            }) {
                Timber.e(it)
                _targetCharactersState.update { currentState ->
                    currentState.copy(loading = false, userMessage = it.getUserMessage())
                }
            }
        }
    }

    fun dismissUserMessage() {
        _uiState.update { it.copy(userMessage = "") }
    }
}



data class TargetCharactersUiState(
    var loading: Boolean = false,
    var data: Int = 0,
    var userMessage: String = "",
)
package com.andresestevez.soreh.ui.screens.characters.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SupportSQLiteQuery
import com.andresestevez.soreh.domain.SearchCharactersUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCharactersUseCase: SearchCharactersUseCase,
) : ViewModel() {

    var filters = MutableStateFlow(CharactersFilter())
    var state = MutableStateFlow(UiState())
        private set
    var targetCharactersState = MutableStateFlow(TargetCharactersUiState())
        private set

    fun searchCharacters(query: SupportSQLiteQuery) {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            searchCharactersUseCase.searchCharacters(query).fold({ characters ->
                state.update { currentState ->
                    currentState.copy(
                        loading = false,
                        data = characters.map { character -> ItemUiState(character) })
                }
                targetCharactersState.update { currentState ->
                    currentState.copy(
                        loading = false,
                        data = characters.size,
                    )
                }
            }) {
                state.update { currentState ->
                    currentState.copy(loading = false, userMessage = it.getUserMessage())
                }
            }
        }
    }

    fun countTargetCharacters(query: SupportSQLiteQuery) {
        viewModelScope.launch {
            targetCharactersState.value = TargetCharactersUiState(loading = true)
            searchCharactersUseCase.countCharacters(query).fold({ count ->
                targetCharactersState.update { currentState ->
                    currentState.copy(
                        loading = false,
                        data = count,
                    )
                }
            }) {
                targetCharactersState.update { currentState ->
                    currentState.copy(loading = false, userMessage = it.getUserMessage())
                }
            }
        }
    }

}



data class TargetCharactersUiState(
    var loading: Boolean = false,
    var data: Int = 0,
    var userMessage: String = "",
)
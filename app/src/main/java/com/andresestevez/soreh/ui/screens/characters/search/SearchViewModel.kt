package com.andresestevez.soreh.ui.screens.characters.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresestevez.soreh.domain.SearchCharactersByNameUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchCharactersByNameUseCase: SearchCharactersByNameUseCase) :
    ViewModel() {

    var state = MutableStateFlow(UiState())
        private set

    fun searchCharactersByName(name: String) {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            searchCharactersByNameUseCase(name).collect { result ->
                state.update { currentState ->
                    result.fold({ characters ->
                        currentState.copy(
                            loading = false,
                            data = characters.map { character -> ItemUiState(character) })

                    }) {
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }
        }
    }

}
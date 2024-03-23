package com.andresestevez.soreh.ui.screens.characters.tops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresestevez.soreh.domain.GetTopsUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.screens.characters.search.CharactersFilter
import com.andresestevez.soreh.ui.screens.characters.search.CharactersQueryBuilder
import com.andresestevez.soreh.ui.screens.characters.search.Publisher
import com.andresestevez.soreh.ui.screens.characters.search.SortingDirection
import com.andresestevez.soreh.ui.screens.characters.search.SortingField
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
class TopsViewModel @Inject constructor(useCase: GetTopsUseCase) : ViewModel() {

    private var _dcUiState = MutableStateFlow(UiState(loading = true))
    val dcUiState = _dcUiState.asStateFlow()

    private val dcQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.DC),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()

    private var _marvelUiState = MutableStateFlow(UiState(loading = true))
    val marvelUiState = _marvelUiState.asStateFlow()

    private val marvelQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.Marvel),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()

    private var _imageUiState = MutableStateFlow(UiState(loading = true))
    val imageUiState = _imageUiState.asStateFlow()

    private val imageQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.Image),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()

    private var _darkUiState = MutableStateFlow(UiState(loading = true))
    val darkUiState = _darkUiState.asStateFlow()

    private val darkQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.DarkHorse),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()

    private var _lucasUiState = MutableStateFlow(UiState(loading = true))
    val lucasUiState = _lucasUiState.asStateFlow()

    private val lucasQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.GeorgeLucas),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()

    private var _nbcUiState = MutableStateFlow(UiState(loading = true))
    val nbcUiState = _nbcUiState.asStateFlow()

    private val nbcQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.NBC),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()



    init {
        viewModelScope.launch {
            launch {
                _dcUiState.update { currentState ->
                    useCase.searchCharactersSuspend(dcQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }

            launch {
                _marvelUiState.update { currentState ->
                    useCase.searchCharactersSuspend(marvelQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }

            launch {
                _imageUiState.update { currentState ->
                    useCase.searchCharactersSuspend(imageQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }

            launch {
                _darkUiState.update { currentState ->
                    useCase.searchCharactersSuspend(darkQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }

            launch {
                _lucasUiState.update { currentState ->
                    useCase.searchCharactersSuspend(lucasQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }

            launch {
                _nbcUiState.update { currentState ->
                    useCase.searchCharactersSuspend(nbcQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }
        }
    }

    fun dismissUserMessage() {
        _dcUiState.update { it.copy(userMessage = "") }
        _marvelUiState.update { it.copy(userMessage = "") }
        _imageUiState.update { it.copy(userMessage = "") }
        _darkUiState.update { it.copy(userMessage = "") }
        _lucasUiState.update { it.copy(userMessage = "") }
        _nbcUiState.update { it.copy(userMessage = "") }
    }

}
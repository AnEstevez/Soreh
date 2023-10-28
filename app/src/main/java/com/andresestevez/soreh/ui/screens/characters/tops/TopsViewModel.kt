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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TopsViewModel @Inject constructor(useCase: GetTopsUseCase) : ViewModel() {

    var dcUiState = MutableStateFlow(UiState(loading = true))
        private set
    private val dcQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.DC),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()

    var marvelUiState = MutableStateFlow(UiState(loading = true))
        private set
    private val marvelQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.Marvel),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()

    var imageUiState = MutableStateFlow(UiState(loading = true))
        private set
    private val imageQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.Image),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()

    var darkUiState = MutableStateFlow(UiState(loading = true))
        private set
    private val darkQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.DarkHorse),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()

    var lucasUiState = MutableStateFlow(UiState(loading = true))
        private set
    private val lucasQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.GeorgeLucas),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()

    var nbcUiState = MutableStateFlow(UiState(loading = true))
        private set
    private val nbcQuery = CharactersQueryBuilder(CharactersFilter(
        publishers = mutableListOf(Publisher.NBC),
        sort = Pair(SortingField.Average, SortingDirection.Desc),
        limit = 10,
    ), false).build()



    init {
        viewModelScope.launch {
            launch {
                dcUiState.update { currentState ->
                    useCase.searchCharactersSuspend(dcQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }

            launch {
                marvelUiState.update { currentState ->
                    useCase.searchCharactersSuspend(marvelQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }

            launch {
                imageUiState.update { currentState ->
                    useCase.searchCharactersSuspend(imageQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }

            launch {
                darkUiState.update { currentState ->
                    useCase.searchCharactersSuspend(darkQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }

            launch {
                lucasUiState.update { currentState ->
                    useCase.searchCharactersSuspend(lucasQuery).fold({ characters ->
                        currentState.copy(loading = false, data = characters.map { ItemUiState(it) })
                    }) {
                        Timber.e(it)
                        currentState.copy(loading = false, userMessage = it.getUserMessage())
                    }
                }
            }

            launch {
                nbcUiState.update { currentState ->
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
        dcUiState.update { it.copy(userMessage = "") }
        marvelUiState.update { it.copy(userMessage = "") }
        imageUiState.update { it.copy(userMessage = "") }
        darkUiState.update { it.copy(userMessage = "") }
        lucasUiState.update { it.copy(userMessage = "") }
        nbcUiState.update { it.copy(userMessage = "") }
    }

}
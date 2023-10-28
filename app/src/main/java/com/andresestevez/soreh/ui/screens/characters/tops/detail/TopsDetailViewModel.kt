package com.andresestevez.soreh.ui.screens.characters.tops.detail

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.andresestevez.soreh.domain.GetTopsUseCase
import com.andresestevez.soreh.domain.ToggleFavoriteUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.navigation.NavCommand.Companion.CURRENT_TOP_CHARACTER
import com.andresestevez.soreh.ui.navigation.NavCommand.Companion.CURRENT_TOP_PUBLISHER
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
class TopsDetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    getTopsUseCase: GetTopsUseCase,
    toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    var state = MutableStateFlow(UiState())
        private set

    var pagesColorState = MutableStateFlow(PagesColorsState())
        private set


    val currentTopCharacter = stateHandle.get<Int>(CURRENT_TOP_CHARACTER) ?: 0

    private val currentTopPublisher = stateHandle.get<Int>(CURRENT_TOP_PUBLISHER) ?: 0

    init {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            getTopsUseCase.searchCharacters(getQueryForPublisher(currentTopPublisher))
                .collect { result ->
                    state.update { currentState ->
                        currentState.copy(
                            loading = true,
                        )
                    }
                    result.fold({ characters ->
                        state.update { currentState ->
                            currentState.copy(
                                loading = false,
                                data = characters.map {
                                    ItemUiState(it).apply {
                                        onClick = { toggleFavoriteUseCase(it) }
                                    }
                                })
                        }
                    }) {
                        state.update { currentState ->
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
        state.update { it.copy(userMessage = "") }
    }

    fun updatePagesBackgroundColor(bitmap: Bitmap, page: Int, isSystemInDarkTheme: Boolean) {
        val palette = Palette.Builder(bitmap).generate()

        val gradientStartColor = if (isSystemInDarkTheme) {
            Color(palette.getDarkVibrantColor(Color.Black.toArgb()))
        } else {
            Color(palette.getLightMutedColor(Color.Black.toArgb()))
        }

        val gradientEndColor = Color(palette.getDominantColor(Color.Black.toArgb()))

        pagesColorState.update { currentState ->
            currentState.getPagesColorStateUpdated(page, Pair(gradientStartColor, gradientEndColor))
        }

    }


    data class PagesColorsState(
        val page0: Pair<Color, Color> = Pair(Color.Black, Color.Black),
        val page1: Pair<Color, Color> = Pair(Color.Black, Color.Black),
        val page2: Pair<Color, Color> = Pair(Color.Black, Color.Black),
        val page3: Pair<Color, Color> = Pair(Color.Black, Color.Black),
        val page4: Pair<Color, Color> = Pair(Color.Black, Color.Black),
        val page5: Pair<Color, Color> = Pair(Color.Black, Color.Black),
        val page6: Pair<Color, Color> = Pair(Color.Black, Color.Black),
        val page7: Pair<Color, Color> = Pair(Color.Black, Color.Black),
        val page8: Pair<Color, Color> = Pair(Color.Black, Color.Black),
        val page9: Pair<Color, Color> = Pair(Color.Black, Color.Black),
    ){


        fun getPagesColorStateUpdated(
            page: Int,
            colors: Pair<Color, Color> = Pair(
                Color.Black,
                Color.Black
            ),
        ): PagesColorsState = when (page) {
            0 -> this.copy(page0 = colors)
            1 -> this.copy(page1 = colors)
            2 -> this.copy(page2 = colors)
            3 -> this.copy(page3 = colors)
            4 -> this.copy(page4 = colors)
            5 -> this.copy(page5 = colors)
            6 -> this.copy(page6 = colors)
            7 -> this.copy(page7 = colors)
            8 -> this.copy(page8 = colors)
            9 -> this.copy(page9 = colors)
            else -> this
        }

        fun getColorsForPage(page: Int): Pair<Color, Color> = when (page) {
            0 -> page0
            1 -> page1
            2 -> page2
            3 -> page3
            4 -> page4
            5 -> page5
            6 -> page6
            7 -> page7
            8 -> page8
            9 -> page9
            else -> page1
        }

    }

}

private fun getQueryForPublisher(publisher: Int) = when (publisher) {

    0 -> CharactersQueryBuilder(
        CharactersFilter(
            publishers = mutableListOf(Publisher.DC),
            sort = Pair(SortingField.Average, SortingDirection.Desc),
            limit = 10,
        ), false
    ).build()

    1 -> CharactersQueryBuilder(
        CharactersFilter(
            publishers = mutableListOf(Publisher.Marvel),
            sort = Pair(SortingField.Average, SortingDirection.Desc),
            limit = 10,
        ), false
    ).build()

    2 -> CharactersQueryBuilder(
        CharactersFilter(
            publishers = mutableListOf(Publisher.Image),
            sort = Pair(SortingField.Average, SortingDirection.Desc),
            limit = 10,
        ), false
    ).build()

    3 -> CharactersQueryBuilder(
        CharactersFilter(
            publishers = mutableListOf(Publisher.DarkHorse),
            sort = Pair(SortingField.Average, SortingDirection.Desc),
            limit = 10,
        ), false
    ).build()

    4 -> CharactersQueryBuilder(
        CharactersFilter(
            publishers = mutableListOf(Publisher.GeorgeLucas),
            sort = Pair(SortingField.Average, SortingDirection.Desc),
            limit = 10,
        ), false
    ).build()

    5 -> CharactersQueryBuilder(
        CharactersFilter(
            publishers = mutableListOf(Publisher.NBC),
            sort = Pair(SortingField.Average, SortingDirection.Desc),
            limit = 10,
        ), false
    ).build()


    else -> CharactersQueryBuilder(
        CharactersFilter(
            publishers = mutableListOf(Publisher.DC),
            sort = Pair(SortingField.Average, SortingDirection.Desc),
            limit = 10,
        ), false
    ).build()
}
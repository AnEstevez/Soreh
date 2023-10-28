package com.andresestevez.soreh.ui.screens.characters.main.detail

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.andresestevez.soreh.domain.GetCharactersByIdList
import com.andresestevez.soreh.domain.ToggleFavoriteUseCase
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.navigation.NavCommand.Companion.CURRENT_MAIN_CHARACTER
import com.andresestevez.soreh.ui.navigation.NavCommand.Companion.MAIN_CHARACTERS_LIST
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainDetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    getCharactersByIdList: GetCharactersByIdList,
    toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    var state = MutableStateFlow(UiState())
        private set

    var pagesColorState = MutableStateFlow(PagesColorsState())
        private set

    val currentMainCharacter = stateHandle.get<Int>(CURRENT_MAIN_CHARACTER) ?: 0

    private val mainCharactersList = stateHandle.get<String>(MAIN_CHARACTERS_LIST) ?: ""

    init {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            getCharactersByIdList(
                idList = mainCharactersList.split(",").map { it.toInt() },
                idListOrder = mainCharactersList
            )
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
    ) {


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

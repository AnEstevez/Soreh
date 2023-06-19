package com.andresestevez.soreh.ui.screens.characters.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.Carousel
import androidx.tv.material3.CarouselDefaults
import androidx.tv.material3.CarouselState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.common.noRippleClickable
import com.andresestevez.soreh.ui.screens.common.thumbWithPalette
import timber.log.Timber

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    appState: SorehAppState,
    onClick: (Int, String) -> Unit = {_,_ ->},
) {

    val state by viewModel.state.collectAsState()

    var initalActiveIndex by rememberSaveable { mutableIntStateOf(0) }
    val carouselState = remember { CarouselState(initialActiveItemIndex = initalActiveIndex) }

    Carousel(
        modifier = Modifier.padding(
            top = appState.scaffoldPadding.value.calculateTopPadding(),
            bottom = appState.navigationBarsInsetsDp.value
        ),
        itemCount = 10,
        carouselState = carouselState,
        autoScrollDurationMillis = 3000,
        carouselIndicator = {
            CarouselDefaults.IndicatorRow(
                itemCount = 10,
                activeItemIndex = carouselState.activeItemIndex,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
            )
        }
    ) { index ->
        if (state.data.isNotEmpty()) {
            Timber.d(state.data.map { itemUiState -> itemUiState.character.id }.joinToString(","))
            Box(modifier = Modifier.noRippleClickable {
                initalActiveIndex = index
                onClick(index, state.data.map { itemUiState -> itemUiState.character.id }.joinToString(","))
            }) {
                thumbWithPalette(thumb = state.data[index].character.thumb)
            }
        }
    }

}



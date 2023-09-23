package com.andresestevez.soreh.ui.screens.characters.main

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.material3.Carousel
import androidx.tv.material3.CarouselDefaults
import androidx.tv.material3.CarouselState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.common.noRippleClickable
import com.andresestevez.soreh.ui.screens.common.thumbWithPalette
import com.commandiron.compose_loading.CubeGrid

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    appState: SorehAppState,
    onClick: (Int, String) -> Unit = { _, _ -> },
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    var initialActiveIndex by rememberSaveable { mutableIntStateOf(0) }
    val carouselState = remember { CarouselState(initialActiveItemIndex = initialActiveIndex) }

    if (state.userMessage.isNotEmpty()) {
        appState.onShowUserMessage(state.userMessage)
        viewModel.dismissUserMessage()
    }
    if (state.loading) {
        Box(modifier = Modifier.fillMaxSize()){
            CubeGrid(
                modifier = Modifier.align(Alignment.Center),
                durationMillis = 800,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    Carousel(
        modifier = Modifier
            .padding(
                top = appState.scaffoldPadding.value.calculateTopPadding(),
                bottom = appState.navigationBarsInsetsDp.value
            )
            .fillMaxSize(),
        itemCount = 10,
        autoScrollDurationMillis = 3000,
        carouselState = carouselState,
        carouselIndicator = {
            CarouselDefaults.IndicatorRow(
                itemCount = 10,
                activeItemIndex = carouselState.activeItemIndex,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
            )
        },
        contentTransformEndToStart = fadeIn(animationSpec = tween(2000))
            .togetherWith(fadeOut(animationSpec = tween(2000))),
        contentTransformStartToEnd = fadeIn(animationSpec = tween(2000))
            .togetherWith(fadeOut(animationSpec = tween(2000)))

    ) { index ->
        if (state.data.isNotEmpty()) {
            Box(modifier = Modifier.noRippleClickable {
                initialActiveIndex = index
                onClick(
                    index,
                    state.data.map { itemUiState -> itemUiState.character.id }.joinToString(",")
                )
            }) {
                thumbWithPalette(thumb = state.data[index].character.thumb)
            }
        }
    }

}



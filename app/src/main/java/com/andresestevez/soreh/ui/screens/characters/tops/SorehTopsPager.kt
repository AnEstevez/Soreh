package com.andresestevez.soreh.ui.screens.characters.tops

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.screens.common.pagerCubeOutDepthTransition

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun SorehTopsPager(
    pagerState: PagerState,
    onClick: (characterPosition: Int, publisher: Int )-> Unit,
    appState: SorehAppState,
    viewModel: TopsViewModel,
) {

    val dcState = viewModel.dcUiState.collectAsState()
    val marvelState = viewModel.marvelUiState.collectAsState()
    val imageState = viewModel.imageUiState.collectAsState()
    val darkState = viewModel.darkUiState.collectAsState()
    val lucasState = viewModel.lucasUiState.collectAsState()
    val nbcState = viewModel.nbcUiState.collectAsState()

    HorizontalPager(
        state = pagerState,
        pageSpacing = 5.dp,
        userScrollEnabled = true,
        reverseLayout = false,
        contentPadding = PaddingValues(5.dp),
        beyondBoundsPageCount = 2,
        pageSize = PageSize.Fill,
        key = null,
        pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
            Orientation.Horizontal
        ),
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pagerCubeOutDepthTransition(page, pagerState)
        ) {
            when (page) {
                0 -> TopsCharacterLazyColumn(
                    page = page,
                    state = dcState.value,
                    onClick = {characterPosition, publisher -> onClick(characterPosition, publisher) },
                    contentPaddingValues = PaddingValues(bottom = appState.scaffoldPadding.value.calculateBottomPadding()),
                )

                1 -> TopsCharacterLazyColumn(
                    page = page,
                    state = marvelState.value,
                    onClick = {characterPosition, publisher -> onClick(characterPosition, publisher) },
                    contentPaddingValues = PaddingValues(bottom = appState.scaffoldPadding.value.calculateBottomPadding()),
                )

                2 -> TopsCharacterLazyColumn(
                    page = page,
                    state = imageState.value,
                    onClick = {characterPosition, publisher -> onClick(characterPosition, publisher) },
                    contentPaddingValues = PaddingValues(bottom = appState.scaffoldPadding.value.calculateBottomPadding()),
                )

                3 -> TopsCharacterLazyColumn(
                    page = page,
                    state = darkState.value,
                    onClick = {characterPosition, publisher -> onClick(characterPosition, publisher) },
                    contentPaddingValues = PaddingValues(bottom = appState.scaffoldPadding.value.calculateBottomPadding()),
                )

                4 -> TopsCharacterLazyColumn(
                    page = page,
                    state = lucasState.value,
                    onClick = {characterPosition, publisher -> onClick(characterPosition, publisher) },
                    contentPaddingValues = PaddingValues(bottom = appState.scaffoldPadding.value.calculateBottomPadding()),
                )

                5 -> TopsCharacterLazyColumn(
                    page = page,
                    state = nbcState.value,
                    onClick = {characterPosition, publisher -> onClick(characterPosition, publisher) },
                    contentPaddingValues = PaddingValues(bottom = appState.scaffoldPadding.value.calculateBottomPadding()),
                )

            }
        }
    }

}
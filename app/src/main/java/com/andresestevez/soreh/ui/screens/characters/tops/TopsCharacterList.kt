package com.andresestevez.soreh.ui.screens.characters.tops

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import com.andresestevez.soreh.ui.screens.common.rememberVectorPainterWithColor
import com.andresestevez.soreh.ui.theme.ShimmerHighlightColor
import com.commandiron.compose_loading.CubeGrid
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.material.placeholder
import timber.log.Timber


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopsCharacterLazyColumn(
    page: Int,
    modifier: Modifier = Modifier,
    state: UiState,
    contentPaddingValues: PaddingValues,
    onClick: (characterPosition: Int, publisher: Int) -> Unit,
    appState: SorehAppState,
    onDismissUserMessage: () -> Unit,
) {

    val numberIcons = listOf(
        R.drawable.outlined_1,
        R.drawable.outlined_2,
        R.drawable.outlined_3,
        R.drawable.outlined_4,
        R.drawable.outlined_5,
        R.drawable.outlined_6,
        R.drawable.outlined_7,
        R.drawable.outlined_8,
        R.drawable.outlined_9,
        R.drawable.outlined_10,
    )

    if (state.userMessage.isNotEmpty()) {
        appState.onShowUserMessage(state.userMessage)
        onDismissUserMessage()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        LazyColumn(
            contentPadding = contentPaddingValues,
        ) {
            state.data.forEachIndexed { index, itemUiState ->
                item(key = itemUiState.character.id) {
                    CharacterListItem(
                        Modifier
                            .animateItemPlacement()
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_xsmall)),
                        character = itemUiState,
                        numberIcons[index],
                    ) { onClick(index, page) }
                }
            }
        }

        if (state.loading) {
            CubeGrid(
                modifier = Modifier.align(Alignment.Center),
                durationMillis = 800,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}


@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier,
    character: ItemUiState,
    @DrawableRes resourceDrawable: Int,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(Modifier.background(MaterialTheme.colorScheme.surface)) {
            Thumb(character)
            Box(
                Modifier
                    .background(MaterialTheme.colorScheme.scrim)
                    .fillMaxSize()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = resourceDrawable),
                    contentDescription = character.character.name,
                    tint = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    modifier = Modifier
                        .height(150.dp)
                        .align(Alignment.Center),
                )
            }
        }
    }

}

@Composable
private fun Thumb(uiState: ItemUiState) {
    var placeholderVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        AsyncImage(
            model = uiState.character.thumb,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(
                    visible = placeholderVisible,
                    placeholderFadeTransitionSpec = { tween(durationMillis = 2000) },
                    contentFadeTransitionSpec = { tween(durationMillis = 2000) },
                    color = Color.LightGray,
                    highlight = PlaceholderHighlight.fade(highlightColor = ShimmerHighlightColor),
                ),
            contentScale = ContentScale.Crop,
            onLoading = { placeholderVisible = true },
            onSuccess = { placeholderVisible = false },
            onError = {
                placeholderVisible = false
                Timber.w("Error loading the image of the character [${uiState.character.name}] with id [${uiState.character.id}] from URL [${uiState.character.thumb}]")
            },
            error = rememberVectorPainterWithColor(
                image = ImageVector.vectorResource(R.drawable.placeholder),
                tintColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            fallback = rememberVectorPainterWithColor(
                image = ImageVector.vectorResource(R.drawable.placeholder),
                tintColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        )

    }
}

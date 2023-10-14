package com.andresestevez.soreh.ui.screens.common

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.theme.ShimmerHighlightColor
import com.commandiron.compose_loading.CubeGrid
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.material.placeholder


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterListVerticalGrid(
    modifier: Modifier = Modifier,
    state: UiState,
    onClick: (Int) -> Unit = {},
    contentPaddingValues: PaddingValues,
    columns: Int = 3,
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        LazyVerticalGrid(
            contentPadding = contentPaddingValues,
            columns = GridCells.Fixed(columns)
        ) {
            items(items = state.data) { itemUiState ->
                CharacterListItem(
                    Modifier
                        .semantics { contentDescription = itemUiState.character.name }
                        .animateItemPlacement()
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_xsmall)),
                    character = itemUiState
                ) { onClick(itemUiState.character.id) }
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
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.padding_xsmall)),
    character: ItemUiState,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
    ) {
        Column {
            Thumb(character)
        }
    }

}

@Composable
private fun Thumb(uiState: ItemUiState) {
    var placeholderVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = uiState.character.thumb,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
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
            onError = { placeholderVisible = false },
            error = rememberVectorPainterWithColor(
                image = ImageVector.vectorResource(R.drawable.placeholder),
                tintColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            fallback = rememberVectorPainterWithColor(
                image = ImageVector.vectorResource(R.drawable.placeholder),
                tintColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
        )

    }
}

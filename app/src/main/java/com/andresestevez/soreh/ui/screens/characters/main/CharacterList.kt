package com.andresestevez.soreh.ui.screens.characters.main

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState


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

        if (state.loading) {
            CircularProgressIndicator(modifier.align(Alignment.Center))
        }

        LazyVerticalGrid(
            contentPadding = contentPaddingValues,
            columns = GridCells.Fixed(columns)
        ) {
            items(items = state.data) { itemUiState ->
                CharacterListItem(
                    character = itemUiState
                ) { onClick(itemUiState.character.id) }
            }
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = uiState.character.thumb,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )

    }
}

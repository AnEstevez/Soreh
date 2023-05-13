package com.andresestevez.soreh.ui.screens.characters.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.screens.characters.ItemUiState
import com.andresestevez.soreh.ui.screens.characters.UiState


@Composable
fun CharacterListVerticalGrid(
    modifier: Modifier = Modifier,
    state: UiState,
    onClick: (ItemUiState) -> Unit = {},
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        if (state.loading) {
            CircularProgressIndicator(modifier.align(Alignment.Center))
        }

        LazyVerticalGrid(
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_xsmall)),
            columns = GridCells.Adaptive(dimensionResource(id = R.dimen.cell_min_width)),
        ) {
            items(items = state.data) { character ->
                CharacterListItem(
                    character = character
                ) { onClick(character) }
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
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
    ) {
        Column {
            Thumb(character)
            Title(character)
        }
    }

}

@Composable
private fun Title(uiState: ItemUiState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    )
    {
        Text(
            text = uiState.character.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center),
            color = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
private fun Thumb(uiState: ItemUiState) {
    Box(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.thumb_height))
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = uiState.character.thumb,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }
}

package com.andresestevez.soreh.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andresestevez.soreh.R
import com.andresestevez.soreh.data.Character
import com.andresestevez.soreh.data.getCharacters

@Composable
fun CharacterListVerticalGrid(
    modifier: Modifier = Modifier,
    onClick: (Character) -> Unit = {},
) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_xsmall)),
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.cell_min_width)),
        modifier = modifier
    ) {
        items(getCharacters()) { character ->
            CharacterListItem(
                character = character,
                onClick = { onClick(character) }
            )
        }
    }
}

@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.padding_xsmall)),
    character: Character,
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
private fun Title(character: Character) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    )
    {
        Text(
            text = character.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center),
            color = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
private fun Thumb(character: Character) {
    Box(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.thumb_height))
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = character.thumb,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (character.type == Character.Type.VIDEO) {
            Icon(
                imageVector = Icons.Default.PlayCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(92.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CharacterListColumn() {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_xsmall)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_xsmall))
    ) {
        items(getCharacters()) { character ->
            CharacterListItem(character = character)
        }
    }
}

@Composable
fun CharacterListRow() {
    LazyRow(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_xsmall)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_xsmall))
    ) {
        items(getCharacters()) { character ->
            CharacterListItem(character = character)
        }
    }
}
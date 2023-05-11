package com.andresestevez.soreh.ui.screens.main

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andresestevez.soreh.R
import com.andresestevez.soreh.data.Character
import com.andresestevez.soreh.framework.remote.RemoteClient
import com.andresestevez.soreh.framework.remote.RemoteService
import com.andresestevez.soreh.framework.remote.dto.CharacterDTO

internal val service: RemoteService = RemoteClient.service

@Composable
fun CharacterListVerticalGrid(
    modifier: Modifier = Modifier,
    onClick: (Character) -> Unit = {},
) {

    var characters by remember { mutableStateOf(emptyList<CharacterDTO>()) }

    LaunchedEffect(Unit) {
        characters = service.searchCharactersByName(
            apiKey = "",
            name = "an"
        ).results
    }

    LazyVerticalGrid(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_xsmall)),
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.cell_min_width)),
        modifier = modifier
    ) {

        items(items = characters) { character ->

            val hero = Character(
                title = character.name,
                id = character.id.toInt(),
                thumb = character.image.url,
                type = Character.Type.PHOTO
            )

            CharacterListItem(
                name = hero.title,
                url = hero.thumb,
                onClick = { onClick(hero) }
            )
        }

    }
}

@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.padding_xsmall)),
    name: String,
    url: String,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
    ) {
        Column {
            Thumb(url)
            Title(name)
        }
    }

}

@Composable
private fun Title(character: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    )
    {
        Text(
            text = character,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center),
            color = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
private fun Thumb(character: String) {
    Box(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.thumb_height))
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = character,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }
}

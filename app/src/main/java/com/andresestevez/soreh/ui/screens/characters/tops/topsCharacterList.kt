package com.andresestevez.soreh.ui.screens.characters.tops

import androidx.annotation.DrawableRes
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopsCharacterLazyColumn(
    modifier: Modifier = Modifier,
    state: UiState,
    contentPaddingValues: PaddingValues,
    onClick: (Int) -> Unit = {},
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
                    ) { onClick(itemUiState.character.id) }
                }
            }
        }


        if (state.loading) {
            CircularProgressIndicator(modifier.align(Alignment.Center))
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
                    contentDescription = null,
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        AsyncImage(
            model = uiState.character.thumb,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(),
            // .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )

    }
}

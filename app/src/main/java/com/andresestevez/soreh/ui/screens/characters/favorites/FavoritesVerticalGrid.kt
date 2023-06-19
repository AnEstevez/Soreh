package com.andresestevez.soreh.ui.screens.characters.favorites

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
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import com.mxalbert.sharedelements.LocalSharedElementsRootScope
import com.mxalbert.sharedelements.SharedMaterialContainer

@Composable
fun FavoritesVerticalGrid(
    modifier: Modifier = Modifier,
    state: UiState,
    onClick: (Int) -> Unit = {},
    contentPaddingValues: PaddingValues,
    columns: Int = 3,
    lazyGridState: LazyGridState,
) {


    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        LazyVerticalGrid(
            contentPadding = contentPaddingValues,
            columns = GridCells.Fixed(columns),
            state = lazyGridState,
        ) {
            items(items = state.data) { itemUiState ->

                SharedMaterialContainer(
                    key = itemUiState.character.id,
                    screenKey = FavoritesScreen,
//                    shape = CircleShape,
                   color = Color.Transparent,
                    transitionSpec = FadeOutTransitionSpec
                ) {
                    val scope = LocalSharedElementsRootScope.current!!

                    Card(
                        modifier = modifier
                            .wrapContentSize()
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_xsmall))
                            .clickable(enabled = !scope.isRunningTransition) {
                                onClick(itemUiState.character.id)
                                scope.changeCharacter(itemUiState.character.id)
                            },
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Column {
                            Thumb(itemUiState)
                        }
                    }
                }

            }
        }


        if (state.loading) {
            CircularProgressIndicator(modifier.align(Alignment.Center))
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
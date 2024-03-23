package com.andresestevez.soreh.ui.screens.common.detail

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import com.andresestevez.soreh.framework.analytics.LocalAnalyticsHelper
import com.andresestevez.soreh.framework.analytics.LogScreenView
import com.andresestevez.soreh.framework.analytics.logBookmark
import com.andresestevez.soreh.framework.analytics.logShare
import com.andresestevez.soreh.ui.SorehScreen
import com.andresestevez.soreh.ui.screens.common.CharacterStats
import com.andresestevez.soreh.ui.screens.common.shareCharacter
import com.andresestevez.soreh.ui.screens.common.thumbWithPalette
import com.andresestevez.soreh.ui.theme.Marcelus
import com.commandiron.compose_loading.CubeGrid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: CharacterDetailViewModel = hiltViewModel(),
) {

    val localAnalyticsHelper = LocalAnalyticsHelper.current
    localAnalyticsHelper.LogScreenView("DetailScreen")

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    var showDetails by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    var palette by remember { mutableStateOf<Palette?>(null) }

    val gradientStartColor = if (isSystemInDarkTheme()) {
        Color(
            palette?.darkVibrantSwatch?.rgb
                ?: MaterialTheme.colorScheme.surface.toArgb()
        )
    } else {
        Color(
            palette?.lightMutedSwatch?.rgb
                ?: MaterialTheme.colorScheme.surface.toArgb()
        )
    }

    val gradientEndColor = Color(
        palette?.dominantSwatch?.rgb
            ?: Color.Black.toArgb()
    )


    if (state.userMessage.isNotEmpty()) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = state.userMessage,
                duration = SnackbarDuration.Short
            )
            viewModel.dismissUserMessage()
        }
    }

    SorehScreen {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(gradientStartColor, gradientEndColor)
                        )
                    ),
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.scrim
                        )

                ) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = padding,
                        verticalArrangement = Arrangement.Center,
                    )
                    {
                        item {
                            OutlinedCard(
                                onClick = { /*TODO*/ },
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                ),
                                border = BorderStroke(
                                    10.dp,
                                    MaterialTheme.colorScheme.surface
                                ),
                                shape = RoundedCornerShape(4),
                                modifier = Modifier
                                    .padding(20.dp)
                                    .shadow(
                                        elevation = 5.dp,
                                        shape = RoundedCornerShape(4),
                                    )
                            ) {
                                Box {
                                    Box(
                                        modifier = Modifier
                                            .background(MaterialTheme.colorScheme.surface)
                                    ) {
                                        palette =
                                            state.data?.character?.let { thumbWithPalette(character = it) }
                                    }

                                    Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                                        ActionButtonsCharacterDetails(
                                            coroutineScope,
                                            state,
                                            context
                                        )
                                    }
                                }


                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(vertical = 5.dp)

                                ) {

                                    Text(
                                        modifier = Modifier
                                            .background(MaterialTheme.colorScheme.surface)
                                            .align(Alignment.Center),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        text = state.data?.character?.name ?: "",
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            fontFamily = Marcelus,
                                            fontWeight = FontWeight.Bold
                                        )

                                    )

                                    IconToggleButton(
                                        checked = showDetails,
                                        onCheckedChange = { showDetails = it },
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    ) {
                                        Icon(
                                            imageVector = if (showDetails) Icons.Filled.ExpandLess
                                            else Icons.Filled.ExpandMore,
                                            contentDescription = null
                                        )
                                    }
                                }

                                Divider(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(horizontal = 20.dp)
                                        .fillMaxWidth()

                                )


                                DetailsInfo(showDetails, state.data?.character)


                                Box(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(
                                            top = 10.dp,
                                            bottom = 17.dp,
                                            start = 20.dp,
                                            end = 20.dp
                                        ),
                                ) {
                                    CharacterStats(state.data?.character)
                                }


                            }
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

    }

}

@Composable
private fun ActionButtonsCharacterDetails(
    coroutineScope: CoroutineScope,
    state: CharacterDetailViewModel.UiState,
    context: Context,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 19.dp, vertical = 15.dp)
    ) {

        val analyticsHelper = LocalAnalyticsHelper.current

        FilledIconButton(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(25),
                    spotColor = MaterialTheme.colorScheme.onSurface
                )
                .size(35.dp),
            onClick = {
                analyticsHelper.logBookmark(
                    isBookmarked = !state.data!!.character.bookmarked,
                    itemId = state.data.character.name
                )
                coroutineScope.launch {
                    state.data.onClick.let { onClick -> onClick() }
                }
            },
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(25)
        ) {
            Crossfade(
                targetState = state.data?.character?.bookmarked == true,
                animationSpec = tween(durationMillis = 1000), label = "bookmarked animation"
            ) { bookmarked ->

                if (bookmarked) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }


        FilledIconButton(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(25),
                    spotColor = MaterialTheme.colorScheme.onSurface
                )
                .size(35.dp),
            onClick = {
                analyticsHelper.logShare(itemId = state.data!!.character.name)
                shareCharacter(
                    context,
                    state.data.character
                )
            },
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(25)
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}



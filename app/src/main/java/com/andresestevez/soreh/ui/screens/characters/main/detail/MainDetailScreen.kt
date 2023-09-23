package com.andresestevez.soreh.ui.screens.characters.main.detail

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.SorehScreen
import com.andresestevez.soreh.ui.screens.common.CharacterStats
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import com.andresestevez.soreh.ui.screens.common.calculateCurrentOffsetForPage
import com.andresestevez.soreh.ui.screens.common.detail.DetailsInfo
import com.andresestevez.soreh.ui.screens.common.rememberVectorPainterWithColor
import com.andresestevez.soreh.ui.screens.common.shareCharacter
import com.andresestevez.soreh.ui.theme.Marcelus
import com.commandiron.compose_loading.CubeGrid
import kotlinx.coroutines.launch
import kotlin.math.abs


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainDetailScreen(
    viewModel: MainDetailViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val pagesColorState by viewModel.pagesColorState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    var showDetails by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val pagerState = rememberPagerState(
        initialPage = viewModel.currentMainCharacter,
        initialPageOffsetFraction = 0f
    ) {
        state.data.size
    }

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
                            listOf(
                                pagesColorState.getColorsForPage(pagerState.currentPage).first,
                                pagesColorState.getColorsForPage(pagerState.currentPage).second
                            )
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

                                val pageOffset = pagerState.calculateCurrentOffsetForPage(page)

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
                                            val isDarkTheme = isSystemInDarkTheme()
                                            ThumbWithAnimation(
                                                state = state,
                                                page = page,
                                                pageOffset = pageOffset,
                                                onSuccess = { bitmap, pageNumber ->
                                                    viewModel.updatePagesBackgroundColor(
                                                        bitmap,
                                                        pageNumber,
                                                        isDarkTheme
                                                    )
                                                })
                                        }

                                        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                                            ActionButtonsCharacterDetails(
                                                state.data[page],
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
                                            text = state.data[page].character.name,
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


                                    DetailsInfo(showDetails, state.data[page].character)

                                    Box(Modifier.background(MaterialTheme.colorScheme.surface)) {
                                        Box(
                                            modifier = Modifier
                                                .height(
                                                    170.dp * (1 - abs(
                                                        pageOffset
                                                    ))
                                                )
                                                .fillMaxWidth()
                                                .graphicsLayer {
                                                    alpha = 1 - abs(pageOffset)
                                                }
                                                .background(MaterialTheme.colorScheme.surface)
                                                .padding(
                                                    top = 10.dp,
                                                    bottom = 17.dp,
                                                    start = 20.dp,
                                                    end = 20.dp
                                                ),
                                        ) {
                                            CharacterStats(state.data[page].character)
                                        }
                                    }

                                }

                            }
                        }
                    }


                    if (state.loading) {
                        CubeGrid(
                            modifier = Modifier.align(Alignment.Center),
                            durationMillis = 800,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }

                }

            }
        }
    }
}


@Composable
private fun ThumbWithAnimation(
    state: UiState,
    page: Int,
    pageOffset: Float,
    onSuccess: (Bitmap, Int) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        AsyncImage(
            model = state.data[page].character.thumb,
            contentDescription = null,
            placeholder = rememberVectorPainter(
                ImageVector.vectorResource(R.drawable.placeholder)
            ),
            error = rememberVectorPainterWithColor(
                image = ImageVector.vectorResource(R.drawable.placeholder),
                tintColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    // get a scale value between 1 and 1.75f, 1.75 will be when its resting,
                    // 1f is the smallest it'll be when the page is focused
                    val scale = lerp(
                        ScaleFactor(1f, 1f),
                        ScaleFactor(1.75f, 1.75f), abs(pageOffset)
                    )
                    // apply the scale equally to both X and Y, to not distort the image
                    scaleX = scale.scaleX
                    scaleY = scale.scaleY
                },
            contentScale = ContentScale.Crop,
            onSuccess = {
                // to avoid java.lang.IllegalStateException: unable to getPixels(), pixel access is not supported on Config#HARDWARE bitmaps
                val bitmap = it.result.drawable.toBitmap().copy(
                    Bitmap.Config.RGBA_F16, true
                )

                onSuccess(bitmap, page)
            }
        )

    }

}

@Composable
private fun ActionButtonsCharacterDetails(
    state: ItemUiState,
    context: Context,
) {

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .padding(horizontal = 19.dp, vertical = 15.dp)
    ) {

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
                coroutineScope.launch {
                    state.onClick()
                }
            },
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(25)
        ) {
            Crossfade(
                targetState = state.character.bookmarked,
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
                shareCharacter(
                    context,
                    state.character
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



package com.andresestevez.soreh.ui.screens.common

import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.RenderVectorGroup
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.screens.characters.detail.CharacterDetailViewModel

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
fun thumbWithPalette(uiState: ItemUiState?): Palette? {

    var palette by remember { mutableStateOf<Palette?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        AsyncImage(
            model = uiState?.character?.thumb,
            contentDescription = null,
            placeholder = rememberVectorPainter(ImageVector.vectorResource(R.drawable.placeholder)),
            error = rememberVectorPainterWithColor(
                image =  ImageVector.vectorResource(R.drawable.placeholder),
                tintColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            onSuccess = {
                // to avoid java.lang.IllegalStateException: unable to getPixels(), pixel access is not supported on Config#HARDWARE bitmaps
                val bitmap = it.result.drawable.toBitmap().copy(Bitmap.Config.RGBA_F16, true)

                palette = Palette.Builder(bitmap).generate()
            }
        )

    }

    return palette
}

@Composable
fun rememberVectorPainterWithColor(image: ImageVector, tintColor: Color) =
    rememberVectorPainter(defaultWidth = image.defaultWidth,
        defaultHeight = image.defaultHeight,
        viewportWidth = image.viewportWidth,
        viewportHeight = image.viewportHeight,
        name = image.name,
        tintColor = tintColor,
        tintBlendMode = image.tintBlendMode,
        autoMirror = false, content = { _, _ -> RenderVectorGroup(group = image.root) }
    )



@Composable
fun CustomProgressBar(
    modifier: Modifier,
    statName: String = "",
    height: Dp = 15.dp,
    backgroundColor: Color = Color.LightGray,
    percent: Int,
    isShownText: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    durationMillis: Int = 1500,
    delayMillis: Int = 300,
    easing: Easing = LinearOutSlowInEasing,
    fontWeightStatValue: FontWeight,
    horizontalPaddingStatValue: Dp,
    statsNameWidthDp: Dp,
    statValueWidthDp: Dp,
) {

    var fullBarWidthPx by remember { mutableIntStateOf(0) }

    val percentCounter by animateIntAsState(
        targetValue = percent,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            easing = easing
        )
    )

    val animatedColor by animateColorAsState(
        targetValue = when {
            (percentCounter < 25) -> MaterialTheme.colorScheme.primary
            (percentCounter > 74) -> MaterialTheme.colorScheme.secondary
            else -> MaterialTheme.colorScheme.tertiary
        },
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            easing = easing
        )
    )

    Row(
        modifier = modifier
            .padding(
                vertical = 2.dp,
            )
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        Box(
            modifier = Modifier
                .width(statsNameWidthDp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .background(MaterialTheme.colorScheme.surface),
                color = MaterialTheme.colorScheme.onSurface,
                text = statName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = textStyle
            )
        }
        Box(
            modifier = Modifier
                .width(statValueWidthDp)
                .padding(horizontal = horizontalPaddingStatValue),
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterEnd),
                text = percentCounter.toString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = textStyle,
                fontWeight = fontWeightStatValue,
                color = animatedColor
            )
        }

        Box(
            modifier = modifier
                .background(backgroundColor)
                .fillMaxWidth()
                .height(height)
                .onGloballyPositioned { coordinates ->
                    fullBarWidthPx = coordinates.size.width
                }
        ) {

            val valueBarWidthDp by animateDpAsState(
                targetValue = (fullBarWidthPx.pxToDp() * percent) / 100,
                animationSpec = tween(
                    durationMillis = durationMillis,
                    delayMillis = delayMillis,
                    easing = easing
                )
            )

            Box(
                modifier = modifier
                    .background(animatedColor)
                    .width(valueBarWidthDp)
                    .fillMaxHeight(),
            ) {
                if (isShownText) Text(
                    text = "$percentCounter % ",
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .padding(horizontal = 5.dp, vertical = 0.dp),
                    style = textStyle,
                )
            }
        }
    }
}


@Composable
fun getMaxStatFieldWidth(
    value: String,
    textStyle: TextStyle,
    fontWeight: FontWeight = FontWeight.Normal,
    horizontalPaddingStatValue: Dp = 0.dp,
): Int {

    var statFieldWidthPx by remember { mutableIntStateOf(0) }

    Layout(content = {
        Text(
            text = value,
            style = textStyle,
            fontWeight = fontWeight,
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(horizontal = horizontalPaddingStatValue)
        )
    },
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map {
                it.measure(
                    constraints = constraints.copy(minWidth = constraints.minWidth)
                )
            }

            statFieldWidthPx = placeables[0].width

            layout(0, 0) {}
        }
    )

    return statFieldWidthPx
}

@Composable
fun CharacterStats(
    state: CharacterDetailViewModel.UiState,
    largestStatName: String = stringResource(id = R.string.intelligence),
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    fontWeightStatValue: FontWeight = FontWeight.SemiBold,
    horizontalPaddingStatValue: Dp = 3.dp,
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {

        val stats: List<Pair<String, Int>> = listOf(
            Pair(
                stringResource(id = R.string.intelligence),
                state.data?.character?.intelligence?.toInt() ?: 0
            ),
            Pair(
                stringResource(id = R.string.strength),
                state.data?.character?.strength?.toInt() ?: 0
            ),
            Pair(
                stringResource(id = R.string.speed),
                state.data?.character?.speed?.toInt() ?: 0
            ),

            Pair(
                stringResource(id = R.string.durability),
                state.data?.character?.durability?.toInt() ?: 0
            ),
            Pair(
                stringResource(id = R.string.power),
                state.data?.character?.power?.toInt() ?: 0
            ),
            Pair(
                stringResource(id = R.string.combat),
                state.data?.character?.combat?.toInt() ?: 0
            ),

        )

        val statNameWidthPx: Int = getMaxStatFieldWidth(
            value = largestStatName,
            textStyle = textStyle
        )

        val statValueWidthPx: Int = getMaxStatFieldWidth(
            value = "100",
            textStyle = textStyle,
            fontWeight = fontWeightStatValue,
            horizontalPaddingStatValue = 3.dp,
        )

        stats.forEach { stat ->
            CustomProgressBar(
                statName = stat.first,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(50)),
                percent = stat.second,
                statsNameWidthDp = statNameWidthPx.pxToDp(),
                statValueWidthDp = statValueWidthPx.pxToDp(),
                fontWeightStatValue = fontWeightStatValue,
                horizontalPaddingStatValue = horizontalPaddingStatValue,
            )
        }
    }
}
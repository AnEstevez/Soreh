package com.andresestevez.soreh.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    secondary = TomiokaRed700Complementary400,
    tertiary = TomiokaYellow700,
    background = TomiokaBlack,
    surface = TomiokaBlack,
    inverseSurface = TomiokaWhiteHighEmphasis,
    onSurface = TomiokaWhiteHighEmphasis,
    scrim = TomiokaWhite200.copy(alpha = 0.2f),
    onSurfaceVariant = TomiokaWhiteMediumEmphasis,
    onSecondaryContainer = TomiokaWhiteDisabled,

    )

private val LightColorScheme = lightColorScheme(
    primary = Color.Black,
    secondary = TomiokaGreen600,
    tertiary = TomiokaYellow700,
    background = Color.White,
    surface = Color.White,
    inverseSurface = TomiokaBlack,
    onSurface = TomiokaBlack,
    scrim = TomiokaBlack.copy(alpha = 0.2f),
    onSurfaceVariant = TomiokaBlackMediumEmphasis,
    onSecondaryContainer = TomiokaBlackdisabled
)

@SuppressLint("RestrictedApi")
@Composable
fun SorehTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {

    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

}
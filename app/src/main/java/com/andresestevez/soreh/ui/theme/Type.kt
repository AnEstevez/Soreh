package com.andresestevez.soreh.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.andresestevez.soreh.R

val Jost = FontFamily(
    Font(R.font.jost_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.jost_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.jost_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.jost_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.jost_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.jost_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.jost_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.jost_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.jost_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.jost_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.jost_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.jost_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.jost_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.jost_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.jost_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.jost_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.jost_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.jost_thinitalic, FontWeight.Thin, FontStyle.Italic),
)

val Marcelus = FontFamily(
    Font(R.font.marcellus_regular, FontWeight.Normal, FontStyle.Normal),
)

// Sort by decreasing size: Display, headline, title, body, and label
// Set of Material typography styles to start with
val Typography = Typography(
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
).defaultFontFamily(Jost)

private fun Typography.defaultFontFamily(fontFamily: FontFamily) = this.copy(
    labelSmall = labelSmall.copy(fontFamily = fontFamily),
    labelMedium = labelMedium.copy(fontFamily = fontFamily),
    labelLarge = labelLarge.copy(fontFamily = fontFamily),
    bodySmall = bodySmall.copy(fontFamily = fontFamily),
    bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
    bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
    titleSmall = titleSmall.copy(fontFamily = fontFamily),
    titleMedium = titleMedium.copy(fontFamily = fontFamily),
    titleLarge = titleLarge.copy(fontFamily = fontFamily),
    headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
    headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
    headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
    displaySmall = displaySmall.copy(fontFamily = fontFamily),
    displayMedium = displayMedium.copy(fontFamily = fontFamily),
    displayLarge = displayLarge.copy(fontFamily = fontFamily),
)

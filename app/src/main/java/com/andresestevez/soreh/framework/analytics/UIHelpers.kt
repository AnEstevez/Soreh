package com.andresestevez.soreh.framework.analytics

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnalyticsHelper = staticCompositionLocalOf<AnalyticsHelper> {
    error("CompositionLocal LocalAnalyticsHelper not present")
}
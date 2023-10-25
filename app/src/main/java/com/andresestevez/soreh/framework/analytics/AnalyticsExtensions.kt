package com.andresestevez.soreh.framework.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.andresestevez.soreh.framework.analytics.AnalyticsEvent.Param

@Composable
fun AnalyticsHelper.LogScreenView(screenName: String) {
    LaunchedEffect(Unit) {
        logEvent(
            AnalyticsEvent(
                type = AnalyticsEvent.Types.SCREEN_VIEW,
                extras = listOf(
                    Param(
                        key = AnalyticsEvent.ParamKeys.SCREEN_VIEW_SCREEN_NAME,
                        value = screenName
                    )
                )
            )
        )
    }
}

fun AnalyticsHelper.logShare(itemId: String) {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.Types.SHARE,
            extras = listOf(
                Param(
                    key = AnalyticsEvent.ParamKeys.SHARE_ITEM_ID,
                    value = itemId
                )
            )
        )
    )
}

fun AnalyticsHelper.logBookmark(isBookmarked: Boolean, itemId: String) {
    val type: String
    val param: String
    if (isBookmarked) {
        type = AnalyticsEvent.Types.BOOKMARKED
        param = AnalyticsEvent.ParamKeys.BOOKMARKED_ITEM_ID
    } else {
        type = AnalyticsEvent.Types.UNBOOKMARKED
        param = AnalyticsEvent.ParamKeys.UNBOOKMARKED_ITEM_ID
    }

    logEvent(
        AnalyticsEvent(
            type = type,
            extras = listOf(
                Param(
                    key = param,
                    value = itemId
                )
            )
        )
    )
}
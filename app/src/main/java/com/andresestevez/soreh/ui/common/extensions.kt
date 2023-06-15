package com.andresestevez.soreh.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import retrofit2.HttpException
import java.io.IOException

internal fun Throwable.getUserMessage(): String = when (this) {
    is IOException -> "Soreh is offline: Check your internet connection"
    is HttpException -> "Server Error ${code()}"
    else -> message?.let { "Unknown Error: $message" } ?: "Unknown Error"
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }
package com.andresestevez.soreh.ui.common

import retrofit2.HttpException
import java.io.IOException

internal fun Throwable.getUserMessage(): String = when (this) {
    is IOException -> "Soreh is offline: Check your internet connection"
    is HttpException -> "Server Error ${code()}"
    else -> message?.let { "Unknown Error: $message" } ?: "Unknown Error"
}

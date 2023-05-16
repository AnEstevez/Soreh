package com.andresestevez.soreh.framework.remote.dto

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class SearchResponse(
    val response: String,
    @Json(name = "results-for")
    val search: String,
    val results: List<CharacterDto>,
)
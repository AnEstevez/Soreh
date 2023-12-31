package com.andresestevez.soreh.framework.remote.dto

import androidx.annotation.Keep

@Keep
data class CharacterDto(
    val appearance: Appearance,
    val biography: Biography,
    val connections: Connections,
    val id: String,
    val image: Image,
    val name: String,
    val powerstats: Powerstats,
    val work: Work
)
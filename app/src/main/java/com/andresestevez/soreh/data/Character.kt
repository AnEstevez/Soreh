package com.andresestevez.soreh.data

import java.util.Date

data class Character(
    val id: Int,
    val name: String,
    val fullName: String ="",
    val height: Int = 0,
    val weight: Int = 0,
    val gender: String ="",
    val race: String ="",
    val occupation: String ="",
    val intelligence: String ="",
    val strength: String ="",
    val speed: String ="",
    val durability: String ="",
    val power: String ="",
    val combat: String ="",
    val alignment: String ="",
    val publisher: String ="",
    val firstAppearance: String ="",
    val groupAffiliation: String ="",
    val relatives: String ="",
    val thumb: String ="",
    val dateModified: Date? = null,
    var bookmarked: Boolean = false,
)
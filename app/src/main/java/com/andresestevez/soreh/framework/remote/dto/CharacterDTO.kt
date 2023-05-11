package com.andresestevez.soreh.framework.remote.dto

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CharacterDTO(
    val appearance: Appearance,
    val biography: Biography,
    val connections: Connections,
    val id: String,
    val image: Image,
    val name: String,
    val powerstats: Powerstats,
    val work: Work
)

@Keep
data class Appearance(
    @Json(name = "eye-color")
    val eyeColor: String,
    val gender: String,
    @Json(name = "hair-color")
    val hairColor: String,
    val height: List<String>,
    val race: String,
    val weight: List<String>
)

@Keep
data class Biography(
    val aliases: List<String>,
    val alignment: String,
    @Json(name = "alter-egos")
    val alterEgos: String,
    @Json(name = "first-appearance")
    val firstAppearance: String,
    @Json(name = "full-name")
    val fullName: String,
    @Json(name = "place-of-birth")
    val placeOfBirth: String,
    val publisher: String
)

@Keep
data class Connections(
    @Json(name = "group-affiliation")
    val groupAffiliation: String,
    val relatives: String
)

@Keep
data class Image(
    val url: String
)

@Keep
data class Powerstats(
    val combat: String,
    val durability: String,
    val intelligence: String,
    val power: String,
    val speed: String,
    val strength: String
)

@Keep
data class Work(
    val base: String,
    val occupation: String
)
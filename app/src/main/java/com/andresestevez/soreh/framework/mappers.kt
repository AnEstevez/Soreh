package com.andresestevez.soreh.framework

import com.andresestevez.soreh.data.models.Character
import com.andresestevez.soreh.framework.local.CharacterEntity
import com.andresestevez.soreh.framework.remote.dto.Appearance
import com.andresestevez.soreh.framework.remote.dto.CharacterByIdResponse
import com.andresestevez.soreh.framework.remote.dto.CharacterDto

private const val NULL = "null"
private const val HYPHEN = "-"
fun CharacterByIdResponse.toDomain(): Character = with(this) {

    val height = getCorrectHeightFromAppearanceDto(appearance)

    val weight = getCorrectWeightFromAppearanceDto(appearance)

    Character(
        id = id.toInt(),
        name = name,
        fullName = if ((biography.fullName ?: name).isEmpty()) name else biography.fullName ?: name,
        height = height,
        weight = weight,
        gender = when (appearance.gender) {
            "Male" -> "Male"
            "Female" -> "Female"
            else -> "Agender"
        },
        race = appearance.race.replace(NULL, HYPHEN),
        occupation = work.occupation.ifEmpty { HYPHEN },
        intelligence = if (powerstats.intelligence == NULL) {
            0
        } else {
            if (powerstats.intelligence.toInt() > 100) 100 else powerstats.intelligence.toInt()
        },
        strength = if (powerstats.strength == NULL) {
            0
        } else {
            if (powerstats.strength.toInt() > 100) 100 else powerstats.strength.toInt()
        },
        speed = if (powerstats.speed == NULL) {
            0
        } else {
            if (powerstats.speed.toInt() > 100) 100 else powerstats.speed.toInt()
        },
        durability = if (powerstats.durability == NULL) {
            0
        } else {
            if (powerstats.durability.toInt() > 100) 100 else powerstats.durability.toInt()
        },
        power = if (powerstats.power == NULL) {
            0
        } else {
            if (powerstats.power.toInt() > 100) 100 else powerstats.power.toInt()
        },
        combat = if (powerstats.combat == NULL) {
            0
        } else {
            if (powerstats.combat.toInt() > 100) 100 else powerstats.combat.toInt()
        },
        alignment = when (biography.alignment) {
            "good" -> "Light side"
            "bad" -> "Dark side"
            "neutral" -> "Neutral"
            else -> HYPHEN
        },
        publisher = biography.publisher.ifEmpty { HYPHEN },
        firstAppearance = (biography.firstAppearance ?: HYPHEN).ifEmpty { HYPHEN },
        groupAffiliation = (connections.groupAffiliation ?: HYPHEN).ifEmpty { HYPHEN },
        relatives = (connections.relatives).ifEmpty { HYPHEN },
        thumb = image.url,
    )
}

fun CharacterDto.toDomain(): Character = with(this) {

    val height = getCorrectHeightFromAppearanceDto(appearance)

    val weight = getCorrectWeightFromAppearanceDto(appearance)

    Character(
        id = id.toInt(),
        name = name,
        fullName = if ((biography.fullName ?: name).isEmpty()) name else biography.fullName ?: name,
        height = height,
        weight = weight,
        gender = when (appearance.gender) {
            "Male" -> "Male"
            "Female" -> "Female"
            else -> HYPHEN
        },
        race = appearance.race.replace("null", HYPHEN),
        occupation = work.occupation.ifEmpty { HYPHEN },
        intelligence = if (powerstats.intelligence == NULL) {
            0
        } else {
            if (powerstats.intelligence.toInt() > 100) 100 else powerstats.intelligence.toInt()
        },
        strength = if (powerstats.strength == NULL) {
            0
        } else {
            if (powerstats.strength.toInt() > 100) 100 else powerstats.strength.toInt()
        },
        speed = if (powerstats.speed == NULL) {
            0
        } else {
            if (powerstats.speed.toInt() > 100) 100 else powerstats.speed.toInt()
        },
        durability = if (powerstats.durability == NULL) {
            0
        } else {
            if (powerstats.durability.toInt() > 100) 100 else powerstats.durability.toInt()
        },
        power = if (powerstats.power == NULL) {
            0
        } else {
            if (powerstats.power.toInt() > 100) 100 else powerstats.power.toInt()
        },
        combat = if (powerstats.combat == NULL) {
            0
        } else {
            if (powerstats.combat.toInt() > 100) 100 else powerstats.combat.toInt()
        },
        alignment = when (biography.alignment) {
            "good" -> "Light side"
            "bad" -> "Dark side"
            "neutral" -> "Neutral"
            else -> HYPHEN
        },
        publisher = biography.publisher.ifEmpty { HYPHEN },
        firstAppearance = (biography.firstAppearance ?: HYPHEN).ifEmpty { HYPHEN },
        groupAffiliation = (connections.groupAffiliation ?: HYPHEN).ifEmpty { HYPHEN },
        relatives = (connections.relatives).ifEmpty { HYPHEN },
        thumb = image.url,
    )
}

//The api returns a few inconsistent weight values
private fun getCorrectWeightFromAppearanceDto(appearance: Appearance): Int {
    val multiplier = if (appearance.weight[1].contains("tons")) "000" else ""

    var weightStr =
        if (appearance.weight[1].contains(" ")) {
            appearance.weight[1].substringBefore(" ")
        } else "0"

    weightStr =
        if (weightStr.contains(",")) {
            weightStr.filterNot { it == ',' }
                .plus(multiplier)
        } else weightStr


    weightStr = weightStr.replace("null", "0")

    return weightStr.toInt()
}

//The api returns a few inconsistent height values
private fun getCorrectHeightFromAppearanceDto(appearance: Appearance): Int {
    var heightStr = if (appearance.height.size > 1 && appearance.height[1].contains(" "))
        appearance.height[1].substringBefore(" ")
    else "0"

    heightStr = if (heightStr.contains(".")) {
        heightStr.filterNot { char -> char == '.' }.plus("0")
    } else if (heightStr.contains(",")) {
        heightStr.substringBefore(",")
    } else {
        heightStr
    }

    return when (heightStr.replace("null", HYPHEN)) {
        "0" -> 0
        else -> heightStr
            .replace("null", "0").toInt()
    }
}


fun CharacterEntity.toDomain() = Character(
    id = id,
    name = name,
    fullName = fullName,
    height = height,
    weight = weight,
    gender = gender,
    race = race,
    occupation = occupation,
    intelligence = intelligence,
    strength = strength,
    speed = speed,
    durability = durability,
    power = power,
    combat = combat,
    alignment = alignment,
    publisher = publisher,
    firstAppearance = firstAppearance,
    groupAffiliation = groupAffiliation,
    relatives = relatives,
    thumb = thumb,
    dateModified = dateModified,
    bookmarked = bookmarked,
)

fun Character.toEntity() = CharacterEntity(
    id = id,
    name = name,
    fullName = fullName,
    height = height,
    weight = weight,
    gender = gender,
    race = race,
    occupation = occupation,
    intelligence = intelligence,
    strength = strength,
    speed = speed,
    durability = durability,
    power = power,
    combat = combat,
    alignment = alignment,
    publisher = publisher,
    firstAppearance = firstAppearance,
    groupAffiliation = groupAffiliation,
    relatives = relatives,
    thumb = thumb,
    dateModified = dateModified,
    bookmarked = bookmarked,
)
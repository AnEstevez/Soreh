package com.andresestevez.soreh.framework

import com.andresestevez.soreh.data.models.Character
import com.andresestevez.soreh.framework.local.CharacterEntity
import com.andresestevez.soreh.framework.remote.dto.Appearance
import com.andresestevez.soreh.framework.remote.dto.CharacterByIdResponse
import com.andresestevez.soreh.framework.remote.dto.CharacterDto

private const val NULL = "null"
private const val ZERO = "0"
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
            else -> HYPHEN
        },
        race = appearance.race.replace(NULL, HYPHEN),
        occupation = work.occupation.ifEmpty { HYPHEN },
        intelligence = if (powerstats.intelligence == NULL) ZERO else powerstats.intelligence,
        strength = if (powerstats.strength == NULL) ZERO else powerstats.strength,
        speed = if (powerstats.speed == NULL) ZERO else powerstats.speed,
        durability = if (powerstats.durability == NULL) ZERO else powerstats.durability,
        power = if (powerstats.power == NULL) ZERO else powerstats.power,
        combat = if (powerstats.combat == NULL) ZERO else powerstats.combat,
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
        intelligence = if (powerstats.intelligence == NULL) ZERO else powerstats.intelligence,
        strength = if (powerstats.strength == NULL) ZERO else powerstats.strength,
        speed = if (powerstats.speed == NULL) ZERO else powerstats.speed,
        durability = if (powerstats.durability == NULL) ZERO else powerstats.durability,
        power = if (powerstats.power == NULL) ZERO else powerstats.power,
        combat = if (powerstats.combat == NULL) ZERO else powerstats.combat,
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
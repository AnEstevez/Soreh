package com.andresestevez.soreh.framework

import com.andresestevez.soreh.data.Character
import com.andresestevez.soreh.framework.local.CharacterEntity
import com.andresestevez.soreh.framework.remote.dto.Appearance
import com.andresestevez.soreh.framework.remote.dto.CharacterByIdResponse
import com.andresestevez.soreh.framework.remote.dto.CharacterDto

fun CharacterByIdResponse.toDomain(): Character = with(this) {

    val height = getCorrectHeightFromAppearanceDto(appearance)

    val weight = getCorrectWeightFromAppearanceDto(appearance)

    Character(
        id = id.toInt(),
        name = name,
        fullName = biography.fullName ?: "",
        height = height,
        weight = weight,
        gender = appearance.gender,
        race = appearance.gender,
        occupation = work.occupation,
        intelligence = powerstats.intelligence,
        strength = powerstats.strength,
        speed = powerstats.speed,
        durability = powerstats.durability,
        power = powerstats.power,
        combat = powerstats.combat,
        alignment = biography.alignment,
        publisher = biography.publisher,
        firstAppearance = biography.firstAppearance ?: "",
        groupAffiliation = connections.groupAffiliation ?: "",
        relatives = connections.relatives,
        thumb = image.url,
    )
}

fun CharacterDto.toDomain(): Character = with(this) {

    val height = getCorrectHeightFromAppearanceDto(appearance)

    val weight = getCorrectWeightFromAppearanceDto(appearance)

    Character(
        id = id.toInt(),
        name = name,
        fullName = biography.fullName ?: "",
        height = height,
        weight = weight,
        gender = appearance.gender,
        race = appearance.gender,
        occupation = work.occupation,
        intelligence = powerstats.intelligence,
        strength = powerstats.strength,
        speed = powerstats.speed,
        durability = powerstats.durability,
        power = powerstats.power,
        combat = powerstats.combat,
        alignment = biography.alignment,
        publisher = biography.publisher,
        firstAppearance = biography.firstAppearance ?: "",
        groupAffiliation = connections.groupAffiliation ?: "",
        relatives = connections.relatives,
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

    return weightStr.toInt()
}

//The api returns a few inconsistent height values
private fun getCorrectHeightFromAppearanceDto(appearance: Appearance): Int {
    val heightStr = if (appearance.height.size > 1 && appearance.height[1].contains(" "))
        appearance.height[1].substringBefore(" ")
    else "0"

    return if (heightStr.contains(".")) {
        heightStr.filterNot { char -> char == '.' }.plus("0").toInt()
    } else if (heightStr.contains(",")) {
        heightStr.substringBefore(",").toInt()
    } else {
        heightStr.toInt()
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
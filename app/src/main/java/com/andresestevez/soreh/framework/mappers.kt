package com.andresestevez.soreh.framework

import com.andresestevez.soreh.data.Character
import com.andresestevez.soreh.framework.remote.dto.CharacterByIdResponse
import com.andresestevez.soreh.framework.remote.dto.CharacterDTO

fun CharacterByIdResponse.toDomain(): Character = with(this) {

    val height = if (appearance.height[1].contains(" ")) appearance.height[1].substringBefore(" ").toInt() else 0
    val weight = if (appearance.weight[1].contains(" ")) appearance.weight[1].substringBefore(" ").toInt() else 0

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

fun CharacterDTO.toDomain(): Character = with(this) {

    val height = if (appearance.height[1].contains(" ")) appearance.height[1].substringBefore(" ").toInt() else 0
    val weight = if (appearance.weight[1].contains(" ")) appearance.weight[1].substringBefore(" ").toInt() else 0

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
package com.andresestevez.testshared

import com.andresestevez.soreh.data.models.Character

fun createCharactersList(vararg args: Int): List<Character> = args.map {
    Character(
        id = it,
        name = "name $it",
        fullName = "fullname $it",
        height = it,
        weight = it,
        gender = "-",
        race = "human $it",
        occupation = "occupation $it",
        intelligence = it,
        strength = it,
        speed = it,
        durability = it,
        power = it,
        combat = it,
        alignment = "alignment $it",
        publisher = "publisher $it",
        firstAppearance = "firstAppearance $it",
        groupAffiliation = "groupAffiliation $it",
        relatives = "relatives $it",
        thumb = "thumb $it",
        dateModified = null,
        bookmarked = false,
    )
}
package com.andresestevez.soreh.ui.screens.characters.search

data class CharactersFilter(
    var name: String = "",
    var genders: MutableList<CharacterFieldValues> = mutableListOf(),
    var alignments: MutableList<CharacterFieldValues> = mutableListOf(),
    var publishers: MutableList<CharacterFieldValues> = mutableListOf(),
    var races: MutableList<CharacterFieldValues> = mutableListOf(),
    var intelligence: ClosedRange<Int> = 0..100,
    var strength: ClosedRange<Int> = 0..100,
    var speed: ClosedRange<Int> = 0..100,
    var durability: ClosedRange<Int> = 0..100,
    var power: ClosedRange<Int> = 0..100,
    var combat: ClosedRange<Int> = 0..100,
    var sort: Pair<SortingField, SortingDirection> = Pair(SortingField.Power, SortingDirection.Asc),
)

interface CharacterFieldValues {
    val value: String
}

enum class Gender(override val value: String) : CharacterFieldValues {
    Male("Male"),
    Female("Female"),
    Agender("-"),
}

enum class Alignment(override val value: String) : CharacterFieldValues {
    Light("Light side"),
    Dark("Dark side"),
    Neutral("Neutral"),
}

enum class Publisher(override val value: String) : CharacterFieldValues {
    Marvel("Marvel Comics"),
    DC("DC Comics"),
    DarkHorse("Dark Horse Comics"),
    NBC("NBC - Heroes"),
    GeorgeLucas("George Lucas"),
    Image("Image Comics"),
}

enum class Race(override val value: String) : CharacterFieldValues {
    Human("Human"),
    Mutant("Mutant"),
    God("God / Eternal"),
    Android("Android"),
    Symbiote("Symbiote"),
    Alien("Alien"),
    Kryptonian("Kryptonian"),
    Demon("Demon"),
    Alpha("Alpha"),
    Asgardian("Asgardian"),
    Atlantean("Atlantean"),
}

enum class SortingField(val value: String) {
    Intelligence("intelligence"),
    Strength("strength"),
    Speed("speed"),
    Durability("durability"),
    Power("power"),
    Combat("combat"),
}

enum class SortingDirection(val value: String) {
    Asc("asc"),
    Desc("desc"),
}
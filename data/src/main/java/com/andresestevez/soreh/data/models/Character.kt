package com.andresestevez.soreh.data.models

import java.util.Date

data class Character(
    val id: Int,
    val name: String,
    val fullName: String ="-",
    val height: Int = 0,
    val weight: Int = 0,
    val gender: String ="-",
    val race: String ="-",
    val occupation: String ="-",
    val intelligence: Int = 0,
    val strength: Int = 0,
    val speed: Int = 0,
    val durability: Int = 0,
    val power: Int = 0,
    val combat: Int = 0,
    val alignment: String ="-",
    val publisher: String ="-",
    val firstAppearance: String ="-",
    val groupAffiliation: String ="-",
    val relatives: String ="-",
    val thumb: String ="-",
    val dateModified: Date? = null,
    var bookmarked: Boolean = false,
){

    val average: Float
        get() = ((intelligence + strength + speed + durability + power + combat).toFloat() / 6)

    fun getFormatedAverage(): String = String.format("%.2f", average)

}
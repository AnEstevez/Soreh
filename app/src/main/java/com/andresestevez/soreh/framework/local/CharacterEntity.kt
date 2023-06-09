package com.andresestevez.soreh.framework.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    var name: String,
    var fullName: String = "",
    var height: Int = 0,
    var weight: Int = 0,
    var gender: String = "",
    var race: String = "",
    var occupation: String = "",
    var intelligence: Int = 0,
    var strength: Int = 0,
    var speed: Int = 0,
    var durability: Int = 0,
    var power: Int = 0,
    var combat: Int = 0,
    var alignment: String = "",
    var publisher: String = "",
    var firstAppearance: String = "",
    var groupAffiliation: String = "",
    var relatives: String = "",
    var thumb: String = "",
    var dateModified: Date?,
    @ColumnInfo(name = "bookmarked", defaultValue = "0")
    var bookmarked: Boolean = false,
)

package com.andresestevez.soreh.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class SorehDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

}


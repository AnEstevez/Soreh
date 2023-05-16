package com.andresestevez.soreh.framework.local

import androidx.room.TypeConverter
import java.util.Date


internal class Converters {

    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(value: Date?): Long? {
        return value?.time
    }
}
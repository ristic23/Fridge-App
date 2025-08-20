package com.fridge.core.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.Date

@ProvidedTypeConverter
class TypeConverts {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}

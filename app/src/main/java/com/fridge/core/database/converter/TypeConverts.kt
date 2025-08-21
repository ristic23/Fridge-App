package com.fridge.core.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@ProvidedTypeConverter
class TypeConverts @Inject constructor() {

    @TypeConverter
    fun fromEpochToLocalDate(value: Long?): LocalDate? =
        value?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() }

    @TypeConverter
    fun localDateToEpoch(date: LocalDate?): Long? =
        date?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()

    @TypeConverter
    fun fromEpochToInstant(value: Long?): Instant? =
        value?.let { Instant.ofEpochMilli(it) }

    @TypeConverter
    fun instantToEpoch(instant: Instant?): Long? =
        instant?.toEpochMilli()
}

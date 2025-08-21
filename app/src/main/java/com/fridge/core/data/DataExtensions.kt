package com.fridge.core.data

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDate.getDayMonthYear(): String {
    return if (this == LocalDate.MAX) {
        ""
    } else {
        this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))
    }
}

fun Instant.getDayMonthYearHourMinutes(): String =
    DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm").format(this.atZone(ZoneId.systemDefault()))

fun LocalDate.toMillis(): Long {
    val date = if (this == LocalDate.MAX || this == LocalDate.MIN) {
        LocalDate.now()
    } else {
        this
    }
    return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}
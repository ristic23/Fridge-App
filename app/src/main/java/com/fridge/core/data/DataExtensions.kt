package com.fridge.core.data

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDate.getDayMonthYear(): String =
    this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))

fun Instant.getDayMonthYearHourMinutes(): String =
    DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm").format(this.atZone(ZoneId.systemDefault()))

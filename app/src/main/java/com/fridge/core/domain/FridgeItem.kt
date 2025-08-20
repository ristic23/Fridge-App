package com.fridge.core.domain

import java.time.Instant
import java.time.LocalDate

data class FridgeItem(
    val id: Int,
    val name: String,
    val note: String? = null,
    val expiredDate: LocalDate,
    val timeStored: Instant,
    val isOpen: Boolean,
    val category: String,
)

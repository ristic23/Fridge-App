package com.fridge.core.domain

import java.time.Instant
import java.time.LocalDate

data class FridgeItem(
    val id: Int = -1,
    val name: String = "",
    val note: String? = null,
    val expiredDate: LocalDate = LocalDate.MAX,
    val timeStored: Instant = Instant.now(),
    val isOpen: Boolean = false,
    val category: String = ""
)

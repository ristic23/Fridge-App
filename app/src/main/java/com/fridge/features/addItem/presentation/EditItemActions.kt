package com.fridge.features.addItem.presentation

import java.time.Instant
import java.time.LocalDate

sealed interface EditItemActions {
    data class Name(val name: String): EditItemActions
    data class Note(val note: String): EditItemActions
    data class TimeStored(val timeStored: Instant): EditItemActions
    data class ExpiredDate(val expiredDate: LocalDate): EditItemActions
    data class Category(val category: com.fridge.core.domain.Category): EditItemActions
    data class IsOpen(val isOpen: Boolean): EditItemActions
}
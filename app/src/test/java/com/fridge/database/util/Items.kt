package com.fridge.database.util

import com.fridge.core.database.entity.FridgeItemEntity
import com.fridge.core.domain.Category
import com.fridge.core.domain.FridgeItem
import java.time.Instant
import java.time.LocalDate

fun getFridgeItem(
    id: Int = 1,
    name: String = "Milk",
    note: String = "Fresh",
    expiredDate: LocalDate = LocalDate.now(),
    timeStored: Instant = Instant.now(),
    isOpen: Boolean = false,
    category: Category = Category.DAIRY
) = FridgeItem(
    id = id,
    name = name,
    note = note,
    expiredDate = expiredDate,
    timeStored = timeStored,
    isOpen = isOpen,
    category = category
)

fun getFridgeEntity(
    id: Int = 1,
    name: String = "Milk",
    note: String = "Fresh",
    expiredDate: LocalDate = LocalDate.now(),
    timeStored: Instant = Instant.now(),
    isOpen: Boolean = false,
    category: Category = Category.DAIRY
) = FridgeItemEntity(
    id = id,
    name = name,
    note = note,
    expiredDate = expiredDate,
    timeStored = timeStored,
    isOpen = isOpen,
    category = category
)

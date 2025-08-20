package com.fridge.core.database.mapper

import com.fridge.core.database.entity.FridgeItemEntity
import com.fridge.core.domain.FridgeItem

fun List<FridgeItemEntity>.toFridgeItems() =
    this.map {
        it.toFridgeItem()
    }

fun FridgeItemEntity.toFridgeItem() = FridgeItem(
    id = id,
    name = name,
    note = note,
    expiredDate = expiredDate,
    timeStored = timeStored,
    isOpen = isOpen,
    category = category,
)
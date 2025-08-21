package com.fridge.core.data

import com.fridge.core.domain.FridgeItem
import java.time.LocalDate

fun FridgeItem.isItemExpired(): Boolean {
    return this.expiredDate.isBefore(LocalDate.now())
}
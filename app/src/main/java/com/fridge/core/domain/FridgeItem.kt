package com.fridge.core.domain

import java.util.Date

data class FridgeItem(
    val id: Int,
    val name: String,
    val note: String? = null,
    val expiredDate: Date,
    val timeStored: Date,
    val isOpen: Boolean,
    val category: String,
)

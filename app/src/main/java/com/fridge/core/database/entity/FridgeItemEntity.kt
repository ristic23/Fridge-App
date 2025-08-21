package com.fridge.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fridge.core.domain.Category
import java.time.Instant
import java.time.LocalDate


@Entity(tableName = "FridgeItemEntity")
data class FridgeItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val note: String? = null,

    val expiredDate: LocalDate,
    val timeStored: Instant,

    val isOpen: Boolean,
    val category: Category
)
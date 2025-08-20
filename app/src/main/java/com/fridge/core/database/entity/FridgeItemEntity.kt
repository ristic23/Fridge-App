package com.fridge.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "FridgeItemEntity")
data class FridgeItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val note: String? = null,

    val expiredDate: Date,
    val timeStored: Date,

    val isOpen: Boolean,
    val category: String
)
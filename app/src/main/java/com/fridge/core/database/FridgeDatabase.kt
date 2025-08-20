package com.fridge.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fridge.core.database.converter.TypeConverts
import com.fridge.core.database.dao.FridgeItemDao
import com.fridge.core.database.entity.FridgeItemEntity

@Database(
    entities = [FridgeItemEntity::class],
    version = 1
)
@TypeConverters(TypeConverts::class)
abstract class FridgeDatabase : RoomDatabase() {

    abstract val fridgeItemDao: FridgeItemDao
}
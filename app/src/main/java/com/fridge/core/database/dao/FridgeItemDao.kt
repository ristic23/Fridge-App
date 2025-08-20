package com.fridge.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fridge.core.database.entity.FridgeItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FridgeItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: FridgeItemEntity)

    @Update
    suspend fun updateItem(item: FridgeItemEntity)

    @Delete
    suspend fun deleteItem(item: FridgeItemEntity)

    @Query("SELECT * FROM FridgeItemEntity WHERE id = :id LIMIT 1")
    suspend fun getItemById(id: Int): FridgeItemEntity?

    @Query("SELECT * FROM FridgeItemEntity")
    fun getAllItems(): Flow<List<FridgeItemEntity>>
}
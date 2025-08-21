package com.fridge.core.database.sources

import com.fridge.core.database.dao.FridgeItemDao
import com.fridge.core.database.mapper.toFridgeEntity
import com.fridge.core.database.mapper.toFridgeItems
import com.fridge.core.domain.FridgeItem
import com.fridge.features.allItems.domain.LocaleAllItemsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomAllItemsSourceImpl @Inject constructor(
    private val fridgeItemDao: FridgeItemDao
) : LocaleAllItemsSource {

    override fun getAllItems(): Flow<List<FridgeItem>> {
        return fridgeItemDao.getAllItems().map {
            it.toFridgeItems()
        }
    }

    override suspend fun insertItem(item: FridgeItem) {
        fridgeItemDao.insertItem(item.toFridgeEntity())
    }

}
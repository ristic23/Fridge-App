package com.fridge.core.database.sources

import com.fridge.core.database.dao.FridgeItemDao
import com.fridge.core.database.mapper.toFridgeEntity
import com.fridge.core.database.mapper.toFridgeItem
import com.fridge.core.domain.FridgeItem
import com.fridge.features.addItem.domain.LocaleAddItemSource
import javax.inject.Inject

class RoomAddItemSourceImpl @Inject constructor(
    private val fridgeItemDao: FridgeItemDao
) : LocaleAddItemSource {

    override suspend fun insertItem(item: FridgeItem) {
        fridgeItemDao.insertItem(item.toFridgeEntity())
    }

    override suspend fun updateItem(item: FridgeItem) {
        fridgeItemDao.updateItem(item.toFridgeEntity())
    }

    override suspend fun getItemById(id: Int): FridgeItem? {
        return fridgeItemDao.getItemById(id).toFridgeItem()
    }

}
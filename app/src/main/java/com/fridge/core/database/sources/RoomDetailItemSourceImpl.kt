package com.fridge.core.database.sources

import com.fridge.core.database.dao.FridgeItemDao
import com.fridge.core.database.mapper.toFridgeEntity
import com.fridge.core.database.mapper.toFridgeItem
import com.fridge.core.domain.FridgeItem
import com.fridge.features.detailsItem.domain.LocaleDetailItemSource
import javax.inject.Inject

class RoomDetailItemSourceImpl @Inject constructor(
    private val fridgeItemDao: FridgeItemDao
) : LocaleDetailItemSource {

    override suspend fun getItemById(id: Int): FridgeItem? {
        return fridgeItemDao.getItemById(id).toFridgeItem()
    }

    override suspend fun deleteItem(item: FridgeItem) {
        fridgeItemDao.deleteItem(item.toFridgeEntity())
    }

}
package com.fridge.features.addItem.data

import com.fridge.core.domain.FridgeItem
import com.fridge.features.addItem.domain.AddItemRepository
import com.fridge.features.addItem.domain.LocaleAddItemSource
import javax.inject.Inject

class AddItemRepositoryImpl @Inject constructor(
    private val localeAddItemSource: LocaleAddItemSource
): AddItemRepository {
    override suspend fun insertItem(item: FridgeItem) {
        localeAddItemSource.insertItem(item)
    }

    override suspend fun updateItem(item: FridgeItem) {
        localeAddItemSource.updateItem(item)
    }

    override suspend fun getItemById(id: Int): FridgeItem? {
        return localeAddItemSource.getItemById(id)
    }
}
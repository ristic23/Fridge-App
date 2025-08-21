package com.fridge.features.addItem.domain

import com.fridge.core.domain.FridgeItem

interface AddItemRepository {

    suspend fun insertItem(item: FridgeItem)

    suspend fun updateItem(item: FridgeItem)

    suspend fun getItemById(id: Int): FridgeItem?
}
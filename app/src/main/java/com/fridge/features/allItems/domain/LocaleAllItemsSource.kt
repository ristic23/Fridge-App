package com.fridge.features.allItems.domain

import com.fridge.core.domain.FridgeItem
import kotlinx.coroutines.flow.Flow

interface LocaleAllItemsSource {
    fun getAllItems():  Flow<List<FridgeItem>>

    suspend fun insertItem(item: FridgeItem)
}
package com.fridge.features.detailsItem.domain

import com.fridge.core.domain.FridgeItem

interface DetailItemRepository {

    suspend fun getItemById(id: Int): FridgeItem?

}
package com.fridge.features.detailsItem.domain

import com.fridge.core.domain.FridgeItem


interface LocaleDetailItemSource {

    suspend fun getItemById(id: Int): FridgeItem?

}
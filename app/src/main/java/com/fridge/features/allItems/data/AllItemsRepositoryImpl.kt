package com.fridge.features.allItems.data

import com.fridge.core.domain.FridgeItem
import com.fridge.features.allItems.domain.AllItemsRepository
import com.fridge.features.allItems.domain.LocaleAllItemsSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllItemsRepositoryImpl @Inject constructor(
//    private val remoteSource: RemoteSourceAllItems,
    private val localeAllItemsSource: LocaleAllItemsSource
) : AllItemsRepository {

    override fun getAllItems(): Flow<List<FridgeItem>> {
        return localeAllItemsSource.getAllItems()
    }

}
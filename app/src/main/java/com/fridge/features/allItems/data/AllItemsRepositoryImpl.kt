package com.fridge.features.allItems.data

import com.fridge.core.domain.FridgeItem
import com.fridge.features.allItems.domain.AllItemsRepository
import com.fridge.features.allItems.domain.LocaleAllItemsSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository implementation for managing all items, following the "Single Source of Truth" pattern.
 *
 * This repository is responsible for coordinating data from two sources:
 * - A local source (e.g., Room database) which is the primary source of data for the UI.
 * - A remote source (e.g., a network API) which is used to refresh the local data.
 *
 * The UI will always observe the local source, and data from the remote source is
 * written to the local source, ensuring consistency and a single point of data truth.
 */
class AllItemsRepositoryImpl @Inject constructor(
//    private val remoteSource: RemoteSourceAllItems,
    private val localeAllItemsSource: LocaleAllItemsSource
) : AllItemsRepository {

    override fun getAllItems(): Flow<List<FridgeItem>> {
        return localeAllItemsSource.getAllItems()
    }

    override suspend fun insertItem(item: FridgeItem) {
        localeAllItemsSource.insertItem(item)
    }

}
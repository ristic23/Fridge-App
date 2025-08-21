package com.fridge.features.detailsItem.data

import com.fridge.core.domain.FridgeItem
import com.fridge.features.detailsItem.domain.DetailItemRepository
import com.fridge.features.detailsItem.domain.LocaleDetailItemSource
import javax.inject.Inject

class DetailItemRepositoryImpl @Inject constructor(
    //    private val remoteSource: RemoteSourceDetailItem,
    private val localeDetailItemSource: LocaleDetailItemSource
): DetailItemRepository {

    override suspend fun getItemById(id: Int): FridgeItem? {
        return localeDetailItemSource.getItemById(id)
    }

    override suspend fun deleteItem(item: FridgeItem) {
        localeDetailItemSource.deleteItem(item)
    }

}
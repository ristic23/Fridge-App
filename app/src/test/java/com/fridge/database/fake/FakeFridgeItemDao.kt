package com.fridge.database.fake

import com.fridge.core.database.dao.FridgeItemDao
import com.fridge.core.database.entity.FridgeItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class FakeFridgeItemDao : FridgeItemDao {

    private val items = mutableListOf<FridgeItemEntity>()
    private val itemsFlow = MutableStateFlow<List<FridgeItemEntity>>(emptyList())
    private val mutex = Mutex()

    override suspend fun insertItem(item: FridgeItemEntity) {
        mutex.withLock {
            val index = items.indexOfFirst { it.id == item.id }
            if (index >= 0) {
                items[index] = item
            } else {
                items.add(item)
            }
            itemsFlow.value = items.toList()
        }
    }

    override suspend fun updateItem(item: FridgeItemEntity) {
        mutex.withLock {
            val index = items.indexOfFirst { it.id == item.id }
            if (index >= 0) {
                items[index] = item
                itemsFlow.value = items.toList()
            }
        }
    }

    override suspend fun deleteItem(item: FridgeItemEntity) {
        mutex.withLock {
            items.removeAll { it.id == item.id }
            itemsFlow.value = items.toList()
        }
    }

    override suspend fun getItemById(id: Int): FridgeItemEntity? {
        return mutex.withLock {
            items.firstOrNull { it.id == id }
        }
    }

    override fun getAllItems(): Flow<List<FridgeItemEntity>> {
        return itemsFlow.asStateFlow()
    }
}
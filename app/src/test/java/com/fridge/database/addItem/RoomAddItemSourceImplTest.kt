package com.fridge.database.addItem

import com.fridge.core.database.dao.FridgeItemDao
import com.fridge.core.database.mapper.toFridgeEntity
import com.fridge.core.database.sources.RoomAddItemSourceImpl
import com.fridge.core.domain.Category
import com.fridge.database.util.getFridgeEntity
import com.fridge.database.util.getFridgeItem
import com.fridge.features.addItem.domain.LocaleAddItemSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

internal class RoomAddItemSourceImplTest {

    private lateinit var localeAddItemSource: LocaleAddItemSource
    private val mockDao: FridgeItemDao = mockk(relaxed = true)

    @Before
    fun setup() {
        localeAddItemSource = RoomAddItemSourceImpl(mockDao)
    }

    private val milkItem = getFridgeItem()
    private val milkEntity = getFridgeEntity()

    @Test
    fun `insertItem should call DAO insertItem`() = runTest {

        localeAddItemSource.insertItem(milkItem)

        coVerify(exactly = 1) { mockDao.insertItem(milkItem.toFridgeEntity()) }
    }

    @Test
    fun `updateItem should call DAO updateItem`() = runTest {

        localeAddItemSource.updateItem(milkItem)

        coVerify(exactly = 1) { mockDao.updateItem(milkItem.toFridgeEntity()) }
    }

    @Test
    fun `getItemById should return mapped item when DAO getItemById returns entity`() = runTest {
        coEvery { mockDao.getItemById(1) } returns milkEntity

        val result = localeAddItemSource.getItemById(1)

        assertEquals(result?.name, "Milk")
        assertEquals(result?.category, Category.DAIRY)
    }

    @Test
    fun `getItemById should return null when DAO getItemById returns null`() = runTest {
        coEvery { mockDao.getItemById(1) } returns null

        val result = localeAddItemSource.getItemById(1)

        assertNull(result)
    }

}
package com.fridge.database.allItems

import com.fridge.core.database.sources.RoomAllItemsSourceImpl
import com.fridge.database.fake.FakeFridgeItemDao
import com.fridge.database.util.getFridgeItem
import com.fridge.features.allItems.domain.LocaleAllItemsSource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class RoomAllItemsSourceImplTest {

    private val fakeFridgeItemDao: FakeFridgeItemDao = FakeFridgeItemDao()
    private lateinit var localeAllItemsSource: LocaleAllItemsSource

    @Before
    fun setUp() {
        localeAllItemsSource = RoomAllItemsSourceImpl(fakeFridgeItemDao)
    }

    @Test
    fun `init check DB is empty`() = runTest {

        val items = localeAllItemsSource.getAllItems().first()

        assertEquals(items.size, 0)
    }

    @Test
    fun `insert item and check DB for it`() = runTest {
        val item = getFridgeItem()

        localeAllItemsSource.insertItem(item)
        val items = localeAllItemsSource.getAllItems().first()

        assertNotEquals(items.size, 0)
        assertEquals(items.contains(item), true)
    }

    @Test
    fun `insert item then update item and check DB for it`() = runTest {
        val item = getFridgeItem()

        localeAllItemsSource.insertItem(item)
        val items = localeAllItemsSource.getAllItems().first()

        assertNotEquals(items.size, 0)
        assertEquals(items.contains(item), true)
    }

    @Test
    fun `inserts all items concurrent and check if appears in flow`() = runTest {
        val job1 = async {
            localeAllItemsSource.insertItem(getFridgeItem(id = 1, name = "Eggs"))
        }
        val job2 = async {
            localeAllItemsSource.insertItem(getFridgeItem(id = 2, name = "Meat"))
        }
        val job3 = async {
            localeAllItemsSource.insertItem(getFridgeItem(id = 3, name = "Juice"))
        }
        job1.await()
        job2.await()
        job3.await()

        val items = localeAllItemsSource.getAllItems().first()

        assertEquals(items.size, 3)
        assertTrue(items.any { it.id == 1 })
        assertTrue(items.any { it.id == 2 })
        assertTrue(items.any { it.id == 3 })
    }
}
@file:OptIn(ExperimentalCoroutinesApi::class)

package com.fridge.features.itemDetails

import app.cash.turbine.test
import com.fridge.database.util.getFridgeItem
import com.fridge.features.detailsItem.domain.DetailItemRepository
import com.fridge.features.detailsItem.presentation.FridgeItemDetailsViewModel
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Empty
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Error
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Loading
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Success
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException

internal class FridgeItemDetailsViewModelTest {

    private val detailItemRepository: DetailItemRepository = mockk(relaxed = true)
    private lateinit var viewModel: FridgeItemDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        viewModel = FridgeItemDetailsViewModel(detailItemRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val item = getFridgeItem()

    @Test
    fun `fetchFridgeItem emits Success`() = runTest {
        coEvery { detailItemRepository.getItemById(item.id) } returns item

        viewModel.screenState.test {
            val initState = awaitItem()
            assertEquals(Loading, initState)

            viewModel.fetchFridgeItem(item.id)
            testDispatcher.scheduler.advanceUntilIdle()

            val successState = awaitItem()
            assertEquals(successState is Success, true)
            assertEquals((successState as Success).item, item)
        }
    }

    @Test
    fun `fetchFridgeItem emits Empty`() = runTest {
        coEvery { detailItemRepository.getItemById(item.id) } returns null

        viewModel.screenState.test {
            val initState = awaitItem()
            assertEquals(Loading, initState)

            viewModel.fetchFridgeItem(item.id)
            testDispatcher.scheduler.advanceUntilIdle()

            val emptyState = awaitItem()
            assertEquals(emptyState is Empty, true)
        }
    }

    @Test
    fun `fetchFridgeItem emits Error`() = runTest {
        val exception = RuntimeException("Error occurred")
        coEvery { detailItemRepository.getItemById(item.id) } throws RuntimeException("Error occurred")

        viewModel.screenState.test {
            val initState = awaitItem()
            assertEquals(Loading, initState)

            viewModel.fetchFridgeItem(item.id)
            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = awaitItem()
            assertEquals(errorState is Error, true)
            assertEquals((errorState as Error).error, exception.message)
        }
    }

    @Test
    fun `fetchFridgeItem emits CancellationException`() = runTest {
        val exception = CancellationException("Cancelled")
        coEvery { detailItemRepository.getItemById(item.id) } throws exception

        viewModel.screenState.test {
            val initState = awaitItem()
            assertEquals(Loading, initState)

            viewModel.fetchFridgeItem(item.id)
            testDispatcher.scheduler.advanceUntilIdle()
        }
    }

    @Test
    fun `deleteItem is Success`() = runTest {
        coEvery { detailItemRepository.getItemById(item.id) } returns item
        coEvery { detailItemRepository.deleteItem(item) } returns Unit

        viewModel.fetchFridgeItem(item.id)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(viewModel.screenState.value is Success, true)

        var callbackCalled = false
        viewModel.deleteItem {
            callbackCalled = true
        }
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { detailItemRepository.deleteItem(item) }
        assertEquals(callbackCalled, true)
    }

    @Test
    fun `deleteItem when screenState is not Success`() = runTest {
        var callbackCalled = false
        viewModel.deleteItem {
            callbackCalled = true
        }
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 0) { detailItemRepository.deleteItem(item) }
        assertEquals(callbackCalled, false)
    }

}
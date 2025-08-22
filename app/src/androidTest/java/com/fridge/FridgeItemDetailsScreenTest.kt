package com.fridge

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.fridge.core.data.getDayMonthYear
import com.fridge.core.data.getDayMonthYearHourMinutes
import com.fridge.core.domain.Category
import com.fridge.core.domain.FridgeItem
import com.fridge.features.detailsItem.presentation.FridgeItemDetailsScreen
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.LocalDate

class FridgeItemDetailsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun onShowingDetailsScreen_initLoadingState() {
        composeRule.setContent {
            FridgeItemDetailsScreen(
                screenState = FridgeItemScreenStates.Loading,
                onBack = {},
                onEditClick = {},
                onDeleteClick = {},
            )
        }

        composeRule.onNodeWithText("Loading…").assertExists()
        composeRule.onNodeWithTag("Circular Loader").assertExists()
        composeRule.onNodeWithTag("Error icon").assertDoesNotExist()
        composeRule.onNodeWithTag("Edit icon").assertDoesNotExist()
        composeRule.onNodeWithTag("Delete icon").assertDoesNotExist()
    }

    @Test
    fun onShowingDetailsScreen_resultIsEmpty() {
        composeRule.setContent {
            FridgeItemDetailsScreen(
                screenState = FridgeItemScreenStates.Empty,
                onBack = {},
                onEditClick = {},
                onDeleteClick = {},
            )
        }

        composeRule.onNodeWithText("Item is empty").assertExists()
        composeRule.onNodeWithTag("Empty icon").assertExists()
        composeRule.onNodeWithText("Loading…").assertDoesNotExist()
        composeRule.onNodeWithTag("Edit icon").assertDoesNotExist()
        composeRule.onNodeWithTag("Delete icon").assertDoesNotExist()
    }

    @Test
    fun onShowingDetailsScreen_resultIsError() {
        composeRule.setContent {
            FridgeItemDetailsScreen(
                screenState = FridgeItemScreenStates.Error,
                onBack = {},
                onEditClick = {},
                onDeleteClick = {},
            )
        }

        composeRule.onNodeWithText("Error").assertExists()
        composeRule.onNodeWithText("Something went wrong, check internet connection, and try again")
            .assertExists()
        composeRule.onNodeWithTag("Error icon").assertExists()
        composeRule.onNodeWithText("Loading…").assertDoesNotExist()
        composeRule.onNodeWithTag("Edit icon").assertDoesNotExist()
        composeRule.onNodeWithTag("Delete icon").assertDoesNotExist()
    }

    @Test
    fun onShowingDetailsScreen_resultIsSuccess() {
        val item = FridgeItem(
            id = 1,
            name = "Eggs",
            category = Category.FOOD,
            isOpen = false,
            note = "These eggs are sourced from free-range hens that are fed a 100% organic diet.",
            expiredDate = LocalDate.now().plusDays(5),
            timeStored = Instant.now(),
        )
        composeRule.setContent {
            FridgeItemDetailsScreen(
                screenState = FridgeItemScreenStates.Success(
                    item = item
                ),
                onBack = {},
                onEditClick = {},
                onDeleteClick = {},
            )
        }

        composeRule.onNodeWithText(item.name).assertExists()
        composeRule.onNodeWithText(item.note ?: "").assertExists()
        composeRule.onNodeWithTag("Note").assertExists()
        composeRule.onNodeWithText(item.expiredDate.getDayMonthYear()).assertExists()
        composeRule.onNodeWithText(item.timeStored.getDayMonthYearHourMinutes()).assertExists()
        composeRule.onNodeWithText("Closed").assertExists()
        composeRule.onNodeWithTag("Closed icon").assertExists()
        composeRule.onNodeWithText("Food").assertExists()

        composeRule.onNodeWithTag("Edit icon").assertExists()
        composeRule.onNodeWithTag("Delete icon").assertExists()

        composeRule.onNodeWithText("Loading…").assertDoesNotExist()
    }

    @Test
    fun onShowingDetailsScreen_resultIsSuccessWithNoNoteAndOpened() {
        val item = FridgeItem(
            id = 1,
            name = "Eggs",
            category = Category.FOOD,
            isOpen = true,
            expiredDate = LocalDate.now().plusDays(5),
            timeStored = Instant.now(),
        )
        composeRule.setContent {
            FridgeItemDetailsScreen(
                screenState = FridgeItemScreenStates.Success(
                    item = item
                ),
                onBack = {},
                onEditClick = {},
                onDeleteClick = {},
            )
        }

        composeRule.onNodeWithText("Opened").assertExists()
        composeRule.onNodeWithTag("Open icon").assertExists()

        composeRule.onNodeWithTag("Note").assertDoesNotExist()
    }

}
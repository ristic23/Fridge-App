package com.fridge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.fridge.features.addItem.presentation.AddItemWrapper
import com.fridge.features.allItems.presentation.AllItemsWrapper
import com.fridge.features.detailsItem.presentation.FridgeItemDetailsWrapper

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(AllItemsScreen)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryProvider = { key ->
            when (key) {
                is AllItemsScreen -> {
                    NavEntry(
                        key = key
                    ) {
                        AllItemsWrapper(
                            onNewItemAction = { backStack.add(EditOrCreateItemScreen(null)) },
                            onItemClick = { backStack.add(DetailItemScreen(it)) }
                        )
                    }
                }

                is DetailItemScreen -> {
                    NavEntry(
                        key = key
                    ) {
                        FridgeItemDetailsWrapper(
                            id = key.id,
                            onBack = { backStack.removeLastOrNull() },
                            onEditClick = { backStack.add(EditOrCreateItemScreen(key.id)) },
                        )
                    }
                }

                is EditOrCreateItemScreen -> {
                    NavEntry(
                        key = key
                    ) {
                        AddItemWrapper(
                            id = key.id,
                            onCancel = { backStack.removeLastOrNull() },
                        )
                    }
                }

                else -> throw RuntimeException("Invalid Screen Destination, Did you forget to add it to the Navigation")
            }
        }
    )
}
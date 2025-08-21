package com.fridge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.fridge.features.addItem.presentation.AddItemWrapper
import com.fridge.features.allItems.presentation.AllItemsWrapper
import com.fridge.features.detailsItem.presentation.FridgeItemDetailsWrapper

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(AllItemsScreen)
    val goBack: () -> Unit = { if (backStack.size > 1) backStack.removeLastOrNull() }

    NavDisplay(
        modifier = modifier,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backStack,
        onBack = { steps -> repeat(steps) { goBack() } },
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
                            onBack = goBack,
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
                            onCancel = goBack,
                        )
                    }
                }

                else -> throw RuntimeException("Invalid Screen Destination, Did you forget to add it to the Navigation")
            }
        }
    )
}
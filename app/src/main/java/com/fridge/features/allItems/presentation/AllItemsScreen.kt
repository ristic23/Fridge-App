package com.fridge.features.allItems.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fridge.core.data.getDayMonthYear
import com.fridge.core.data.getDayMonthYearHourMinutes
import com.fridge.core.domain.FridgeItem
import com.fridge.features.allItems.presentation.components.ItemFridge
import com.fridge.ui.theme.FridgeAppTheme
import java.time.Instant
import java.time.LocalDate

@Composable
fun AllItemsWrapper(
    viewModel: AllItemsViewModel = hiltViewModel(),
    onNewItemAction: () -> Unit,
    onItemClick: (Int) -> Unit,
) {

    val items by viewModel.items.collectAsStateWithLifecycle(emptyList())

    AllItemsScreen(
        items = items,
        onNewItemAction = onNewItemAction,
        onItemClick = onItemClick,
    )

}

@Composable
fun AllItemsScreen(
    items: List<FridgeItem>,
    onNewItemAction: () -> Unit,
    onItemClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                count = items.size,
                key = { items[it].id }
            ) {
                with(items[it]) {
                    ItemFridge(
                        modifier = Modifier
                            .fillMaxWidth(),
                        name = name,
                        note = note,
                        category = category,
                        timeStored = timeStored.getDayMonthYearHourMinutes(),
                        expiredDate = expiredDate.getDayMonthYear(),
                        isOpened = isOpen,
                        onItemClick = { onItemClick(id) },
                    )
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            onClick = onNewItemAction,
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "FAB Icon")
        }
    }
}

@Preview
@Composable
private fun AllItemsScreenPreview() {
    FridgeAppTheme {
        AllItemsScreen(
            items = listOf(
                FridgeItem(
                    id = 1,
                    name = "Eggs",
                    category = "Protein",
                    isOpen = true,
                    note = "These eggs are sourced from free-range hens that are fed a 100% organic diet.",
                    expiredDate = LocalDate.now().plusDays(5),
                    timeStored = Instant.now(),
                )
            ),
            onNewItemAction = {},
            onItemClick = {},
        )
    }
}
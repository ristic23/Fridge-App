@file:OptIn(ExperimentalMaterial3Api::class)

package com.fridge.features.allItems.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fridge.R
import com.fridge.core.data.getDayMonthYear
import com.fridge.core.data.getDayMonthYearHourMinutes
import com.fridge.core.data.isItemExpired
import com.fridge.core.designComponents.FilterToggleCard
import com.fridge.core.designComponents.menu.SortDropDownMenu
import com.fridge.core.domain.Category
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
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle("")
    val isExpiredOn by viewModel.isExpiredOn.collectAsStateWithLifecycle(false)
    val isOpenedOn by viewModel.isOpenedOn.collectAsStateWithLifecycle(false)
    val sortField by viewModel.sortField.collectAsStateWithLifecycle(SortField.Name)
    val sortOrder by viewModel.sortOrder.collectAsStateWithLifecycle(SortOrder.Ascending)

    AllItemsScreen(
        items = items,
        searchQuery = searchQuery,
        onNewItemAction = onNewItemAction,
        isExpiredOn = isExpiredOn,
        sortField = sortField,
        sortOrder = sortOrder,
        isOpenedOn = isOpenedOn,
        onItemClick = onItemClick,
        onAction = viewModel::onAction,
    )

}

@Composable
fun AllItemsScreen(
    items: List<FridgeItem>,
    searchQuery: String,
    isExpiredOn: Boolean,
    isOpenedOn: Boolean,
    sortField: SortField,
    sortOrder: SortOrder,
    onNewItemAction: () -> Unit,
    onAction: (AllItemsAction) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    var isSortDropMenuShown by rememberSaveable {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()

                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                    focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                singleLine = true,
                maxLines = 1,
                value = searchQuery,
                onValueChange = {
                    onAction(AllItemsAction.Search(it))
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Search icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .then(
                                if (searchQuery.isNotBlank()) {
                                    Modifier
                                        .clickable {
                                            onAction(AllItemsAction.Search(""))
                                        }
                                } else {
                                    Modifier.alpha(0f)
                                }
                            ),
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = "Search icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.searchHint),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Filters"
                )
                Text(
                    text = "Sort"
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    FilterToggleCard(
                        modifier = Modifier,
                        text = stringResource(id = R.string.isExpired),
                        isOn = isExpiredOn,
                        onCardClick = {
                            onAction(AllItemsAction.IsExpired)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterToggleCard(
                        modifier = Modifier,
                        text = stringResource(id = R.string.opened),
                        isOn = isOpenedOn,
                        onCardClick = {
                            onAction(AllItemsAction.IsOpen)
                        }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier,
                        onClick = {
                            isSortDropMenuShown = true
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            Text(
                                text = stringResource(
                                    when (sortField) {
                                        SortField.ExpiredDate -> R.string.expiredSort
                                        SortField.Name -> R.string.nameSort
                                        SortField.TimeStored -> R.string.storedSort
                                    }
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable {
                                onAction(AllItemsAction.SortOrder)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(
                                when (sortOrder) {
                                    SortOrder.Ascending -> R.string.asc
                                    SortOrder.Descending -> R.string.dsc
                                }
                            ),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                SortDropDownMenu(
                    expanded = isSortDropMenuShown,
                    onDismiss = {
                        isSortDropMenuShown = false
                    },
                    onActionFirst = {
                        onAction(AllItemsAction.Sort(SortField.Name))
                        isSortDropMenuShown = false
                    },
                    onActionSecond = {
                        onAction(AllItemsAction.Sort(SortField.ExpiredDate))
                        isSortDropMenuShown = false
                    },
                    onActionThird = {
                        onAction(AllItemsAction.Sort(SortField.TimeStored))
                        isSortDropMenuShown = false
                    },
                )
            }


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
                            category = category.displayName,
                            timeStored = timeStored.getDayMonthYearHourMinutes(),
                            expiredDate = expiredDate.getDayMonthYear(),
                            isOpened = isOpen,
                            isExpired = isItemExpired(),
                            onItemClick = { onItemClick(id) },
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 22.dp, end = 22.dp),
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
                    category = Category.FOOD,
                    isOpen = true,
                    note = "These eggs are sourced from free-range hens that are fed a 100% organic diet.",
                    expiredDate = LocalDate.now().plusDays(5),
                    timeStored = Instant.now(),
                )
            ),
            onNewItemAction = {},
            onItemClick = {},
            onAction = {},
            searchQuery = "",
            sortField = SortField.Name,
            sortOrder = SortOrder.Ascending,
            isExpiredOn = false,
            isOpenedOn = false,
        )
    }
}
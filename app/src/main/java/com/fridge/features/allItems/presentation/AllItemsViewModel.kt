package com.fridge.features.allItems.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fridge.core.data.Cache
import com.fridge.core.data.isItemExpired
import com.fridge.core.domain.Category
import com.fridge.core.domain.FridgeItem
import com.fridge.features.allItems.domain.AllItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AllItemsViewModel @Inject constructor(
    private val allItemsRepository: AllItemsRepository,
    private val cache: Cache,
) : ViewModel() {

    private val _items = MutableStateFlow<List<FridgeItem>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    private val _filterIsOpen = MutableStateFlow(false)
    val isOpenedOn: StateFlow<Boolean> = _filterIsOpen.asStateFlow()
    private val _filterExpired = MutableStateFlow(false)
    val isExpiredOn: StateFlow<Boolean> = _filterExpired.asStateFlow()
    private val _sortField: MutableStateFlow<SortField> = MutableStateFlow(SortField.Name)
    val sortField: StateFlow<SortField> = _sortField.asStateFlow()
    private val _sortOrder: MutableStateFlow<SortOrder> = MutableStateFlow(SortOrder.Ascending)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    val items: StateFlow<List<FridgeItem>> = combine(
        _searchQuery,
        _filterIsOpen,
        _filterExpired,
        _sortField,
        _sortOrder
    ) { query, isOpen, isExpired, sortField, sortOrder ->
        applyFiltersAndSort(
            items = _items.value,
            searchQuery = query,
            filterIsOpen = isOpen,
            filterExpired = isExpired,
            sortField = sortField,
            sortOrder = sortOrder
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    init {
        if (cache.getBoolean("isFirstUse", true)) {
            viewModelScope.launch(Dispatchers.IO) {
                allItemsRepository.insertItem(
                    FridgeItem(
                        id = 1,
                        name = "Milk",
                        category = Category.DAIRY,
                        isOpen = false,
                        note = "Moja kravica, 2.8% masti, kupljeno na 30% akciji u lokalnoj mlekari.",
                        expiredDate = LocalDate.now().plusDays(5),
                        timeStored = Instant.now(),
                    )
                )
                allItemsRepository.insertItem(
                    FridgeItem(
                        id = 2,
                        name = "Beef Meat",
                        category = Category.MEAT,
                        isOpen = false,
                        note = "Grass fed beef.",
                        expiredDate = LocalDate.now().plusDays(7),
                        timeStored = Instant.now(),
                    )
                )
                cache.saveBoolean("isFirstUse", false)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            allItemsRepository.getAllItems().collectLatest {
                _items.value = it
            }
        }
    }

    fun onAction(action: AllItemsAction) {
        when (action) {
            is AllItemsAction.Search -> {
                _searchQuery.value = action.query
            }

            AllItemsAction.IsExpired -> {
                _filterExpired.value = !isExpiredOn.value
            }

            AllItemsAction.IsOpen -> {
                _filterIsOpen.value = !isOpenedOn.value
            }

            AllItemsAction.SortOrder -> {
                _sortOrder.value = if (sortOrder.value == SortOrder.Ascending) {
                    SortOrder.Descending
                } else {
                    SortOrder.Ascending
                }
            }

            is AllItemsAction.Sort -> {
                _sortField.value = action.sort
            }
        }
    }

    fun applyFiltersAndSort(
        items: List<FridgeItem>,
        searchQuery: String,
        filterIsOpen: Boolean,
        filterExpired: Boolean,
        sortField: SortField,
        sortOrder: SortOrder
    ): List<FridgeItem> {
        return items
            .filter { item ->
                searchQuery.isBlank() || item.name.contains(searchQuery, ignoreCase = true)
            }
            .filter { item ->
                if (filterIsOpen) {
                    item.isOpen
                } else {
                    true
                }
            }
            .filter { item ->
                if (filterExpired) {
                    item.isItemExpired()
                } else {
                    true
                }
            }
            .sortedWith(compareBy<FridgeItem> {
                when (sortField) {
                    SortField.Name -> it.name.lowercase()
                    SortField.ExpiredDate -> it.expiredDate
                    SortField.TimeStored -> it.timeStored
                }
            }.let { comparator ->
                if (sortOrder == SortOrder.Descending) comparator.reversed() else comparator
            })
    }

}
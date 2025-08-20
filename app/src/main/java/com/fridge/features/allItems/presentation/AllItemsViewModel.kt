package com.fridge.features.allItems.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fridge.core.domain.FridgeItem
import com.fridge.features.allItems.domain.AllItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllItemsViewModel @Inject constructor(
    private val allItemsRepository: AllItemsRepository
): ViewModel() {

    private val _items = MutableStateFlow<List<FridgeItem>>(emptyList())
    val items: StateFlow<List<FridgeItem>> = _items.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            allItemsRepository.getAllItems().collectLatest {
                _items.value = it
            }
        }
    }

}
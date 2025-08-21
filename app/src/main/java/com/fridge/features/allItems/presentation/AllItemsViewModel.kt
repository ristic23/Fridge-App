package com.fridge.features.allItems.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fridge.core.data.Cache
import com.fridge.core.domain.FridgeItem
import com.fridge.features.allItems.domain.AllItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AllItemsViewModel @Inject constructor(
    private val allItemsRepository: AllItemsRepository,
    private val cache: Cache,
): ViewModel() {

    private val _items = MutableStateFlow<List<FridgeItem>>(emptyList())
    val items: StateFlow<List<FridgeItem>> = _items.asStateFlow()

    init {
        if (cache.getBoolean("isFirstUse", true)) {
            viewModelScope.launch(Dispatchers.IO) {
                allItemsRepository.insertItem(
                    FridgeItem(
                        id = 1,
                        name = "Milk",
                        category = "Diary",
                        isOpen = false,
                        note = "Moja kravica, 2.8% masti, kupljeno na 30% akciji u lokalnoj mlekari.",
                        expiredDate = LocalDate.now().plusDays(5),
                        timeStored = Instant.now(),
                    )
                )
                allItemsRepository.insertItem(
                    FridgeItem(
                        id = 2,
                        name = "Meat",
                        category = "Protein",
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

}
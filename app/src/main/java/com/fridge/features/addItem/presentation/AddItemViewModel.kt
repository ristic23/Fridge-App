package com.fridge.features.addItem.presentation

import androidx.lifecycle.ViewModel
import com.fridge.core.domain.FridgeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AddItemViewModel @Inject constructor(

): ViewModel() {

    private val _editItem: MutableStateFlow<FridgeItem?> = MutableStateFlow(null)
    val editItem: StateFlow<FridgeItem?> = _editItem.asStateFlow()
}
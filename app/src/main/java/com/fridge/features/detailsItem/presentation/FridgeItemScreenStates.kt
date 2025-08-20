package com.fridge.features.detailsItem.presentation

import com.fridge.core.domain.FridgeItem

sealed interface FridgeItemScreenStates {
    data object Loading : FridgeItemScreenStates
    data class Error(val error: String) : FridgeItemScreenStates
    data object Empty : FridgeItemScreenStates
    data class Success(val item: FridgeItem) : FridgeItemScreenStates
}
package com.fridge.features.detailsItem.presentation

import com.fridge.core.domain.FridgeItem

sealed interface FridgeItemScreenStates {
    data object Loading : FridgeItemScreenStates
    data object Error : FridgeItemScreenStates
    data object Empty : FridgeItemScreenStates
    data class Success(val item: FridgeItem) : FridgeItemScreenStates
}
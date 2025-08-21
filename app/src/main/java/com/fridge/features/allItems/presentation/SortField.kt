package com.fridge.features.allItems.presentation

sealed interface SortField {
    object Name: SortField
    object ExpiredDate: SortField
    object TimeStored: SortField
}
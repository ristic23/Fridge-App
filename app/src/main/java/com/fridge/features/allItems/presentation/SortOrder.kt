package com.fridge.features.allItems.presentation

sealed interface SortOrder {
    object Ascending: SortOrder
    object Descending: SortOrder
}
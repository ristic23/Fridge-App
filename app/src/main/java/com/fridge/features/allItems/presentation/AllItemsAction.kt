package com.fridge.features.allItems.presentation

sealed interface AllItemsAction {
    data class Search(val query: String): AllItemsAction
    object IsOpen: AllItemsAction
    object IsExpired: AllItemsAction
    object SortOrder: AllItemsAction
    data class Sort(val sort: SortField): AllItemsAction
}
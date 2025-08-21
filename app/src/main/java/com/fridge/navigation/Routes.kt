package com.fridge.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object AllItemsScreen : NavKey

@Serializable
data class DetailItemScreen(val id: Int) : NavKey

@Serializable
data class EditOrCreateItemScreen(val id: Int?) : NavKey
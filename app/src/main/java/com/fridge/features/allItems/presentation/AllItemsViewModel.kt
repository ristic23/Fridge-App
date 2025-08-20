package com.fridge.features.allItems.presentation

import androidx.lifecycle.ViewModel
import com.fridge.features.allItems.domain.AllItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllItemsViewModel @Inject constructor(
    private val allItemsRepository: AllItemsRepository
): ViewModel() {



}
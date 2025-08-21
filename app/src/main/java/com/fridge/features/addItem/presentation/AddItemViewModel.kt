package com.fridge.features.addItem.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fridge.core.domain.FridgeItem
import com.fridge.features.addItem.domain.AddItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val addItemRepository: AddItemRepository
) : ViewModel() {

    private val _editItem: MutableStateFlow<FridgeItem> = MutableStateFlow(FridgeItem())
    val editItem: StateFlow<FridgeItem> = _editItem.asStateFlow()

    fun onAction(action: EditItemActions) {
        _editItem.update { current ->
            when (action) {
                is EditItemActions.Category -> current.copy(category = action.category)
                is EditItemActions.ExpiredDate -> current.copy(expiredDate = action.expiredDate)
                is EditItemActions.IsOpen -> current.copy(isOpen = action.isOpen)
                is EditItemActions.Name -> current.copy(name = action.name)
                is EditItemActions.Note -> current.copy(note = action.note)
                is EditItemActions.TimeStored -> current.copy(timeStored = action.timeStored)
            }
        }
    }

    fun fetchFridgeItem(id: Int) {
        viewModelScope.launch {
            try {
                addItemRepository.getItemById(id)?.let {
                    _editItem.value = it
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("AddItemVM", "Failed to fetch item", e)
            }
        }
    }

    fun saveItem(
        saveFinished: () -> Unit,
    ) {
        if (fridgeItemNotFiled()) return
        viewModelScope.launch(Dispatchers.IO) {
            if (editItem.value.id == -1) {
                addItemRepository.insertItem(editItem.value)
            } else {
                addItemRepository.updateItem(editItem.value)
            }
            saveFinished()
        }
    }

    private fun fridgeItemNotFiled(): Boolean {
        return editItem.value.let { item ->
            item.name.isBlank() ||
                    item.category.isBlank() ||
                    item.expiredDate == LocalDate.MAX
        }
    }

}

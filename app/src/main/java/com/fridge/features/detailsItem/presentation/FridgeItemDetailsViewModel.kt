package com.fridge.features.detailsItem.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fridge.features.detailsItem.domain.DetailItemRepository
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Empty
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Error
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Loading
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class FridgeItemDetailsViewModel @Inject constructor(
    private val detailItemRepository: DetailItemRepository
) : ViewModel() {

    private val _screenState: MutableStateFlow<FridgeItemScreenStates> = MutableStateFlow(Loading)
    val screenState: StateFlow<FridgeItemScreenStates> = _screenState.asStateFlow()

    fun fetchFridgeItem(id: Int) {
        _screenState.value = Loading
        viewModelScope.launch {
            try {
                val result = detailItemRepository.getItemById(id)
                _screenState.value = result?.let { Success(it) } ?: Empty
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                _screenState.value = Error
            }
        }
    }

    fun deleteItem(
        deletingFinished: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (screenState.value is Success) {
                detailItemRepository.deleteItem(item = (screenState.value as Success).item)
                deletingFinished()
            }
        }
    }

}
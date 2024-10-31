package com.kdroid.kmplog.client.presentation.toast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.domain.UiMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class ToastViewModel() : ViewModel(){
    private var id = 0
    private val _uiState = MutableStateFlow(UiState())
    open val uiState: StateFlow<UiState> = _uiState

    fun loadToSuccess() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        delay(1000)
        _uiState.update {
            val messages = it.uiMessages.toMutableList()
            messages.add(UiMessage.Success("Loaded ${id++}"))

            it.copy(isLoading = false, uiMessages = messages)
        }
    }

    fun loadToFailure() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        delay(1000)
        _uiState.update {
            val messages = it.uiMessages.toMutableList()
            messages.add(UiMessage.Error("Failed"))
            it.copy(isLoading = false, uiMessages = messages)
        }
    }

    fun removeUiMessageById(id: Long) {
        val index = uiState.value.uiMessages.indexOfFirst { it.id == id }
        if (index != -1) {
            _uiState.update { state ->
                val list = state.uiMessages.toMutableList()
                list.removeAt(index)
                state.copy(uiMessages = list)
            }
        }
    }
}
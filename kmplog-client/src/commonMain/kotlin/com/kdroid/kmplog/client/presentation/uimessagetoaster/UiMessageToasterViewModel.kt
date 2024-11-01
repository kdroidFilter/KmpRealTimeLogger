package com.kdroid.kmplog.client.presentation.uimessagetoaster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.domain.UiMessageToaster
import com.kdroid.kmplog.client.kmplog_client.generated.resources.Res
import com.kdroid.kmplog.client.kmplog_client.generated.resources.connected_successfully
import com.kdroid.kmplog.client.kmplog_client.generated.resources.disconnected
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

open class UiMessageToasterViewModel() : ViewModel(){
    private var id = 0
    private val _uiMessageToasterState = MutableStateFlow(UiMessageToasterState())
    open val uiMessageToasterState: StateFlow<UiMessageToasterState> = _uiMessageToasterState

    fun loadToSuccess() = viewModelScope.launch {
        _uiMessageToasterState.update { it.copy(isLoading = true) }
        _uiMessageToasterState.update {
            val messages = it.uiMessageToasters.toMutableList()
            messages.add(UiMessageToaster.Success(getString(Res.string.connected_successfully)))

            it.copy(isLoading = false, uiMessageToasters = messages)
        }
    }

    fun loadToFailure() = viewModelScope.launch {
        _uiMessageToasterState.update { it.copy(isLoading = true) }
        _uiMessageToasterState.update {
            val messages = it.uiMessageToasters.toMutableList()
            messages.add(UiMessageToaster.Error(getString(Res.string.disconnected)))
            it.copy(isLoading = false, uiMessageToasters = messages)
        }
    }

    fun removeUiMessageById(id: Long) {
        val index = uiMessageToasterState.value.uiMessageToasters.indexOfFirst { it.id == id }
        if (index != -1) {
            _uiMessageToasterState.update { state ->
                val list = state.uiMessageToasters.toMutableList()
                list.removeAt(index)
                state.copy(uiMessageToasters = list)
            }
        }
    }
}
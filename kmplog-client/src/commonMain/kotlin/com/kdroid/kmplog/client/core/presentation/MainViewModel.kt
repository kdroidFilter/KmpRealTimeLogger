package com.kdroid.kmplog.client.core.presentation

import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.core.data.network.connectionStatus
import com.kdroid.kmplog.client.core.presentation.uimessagetoaster.UiMessageToasterViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel : UiMessageToasterViewModel() {
    val isConnected = MutableStateFlow(false)
    private var wasConnectedPreviously: Boolean = false

    init {
        observeConnectionStatus()
    }

    fun onEvent(events: MainEvents) {
        when (events) {
            is MainEvents.removeUiMessageById -> removeUiMessageById(events.id)
            MainEvents.openGithubPage -> openGithubPage()
        }
    }

    private fun observeConnectionStatus() {
        viewModelScope.launch {
            connectionStatus.collectLatest { connected ->
                isConnected.value = connected

                if (connected && !wasConnectedPreviously) {
                    displayConnectedToast()
                    wasConnectedPreviously = true
                } else if (!connected && wasConnectedPreviously) {
                    displayDisconnectedToast()
                    wasConnectedPreviously = false
                }
            }
        }
    }
}
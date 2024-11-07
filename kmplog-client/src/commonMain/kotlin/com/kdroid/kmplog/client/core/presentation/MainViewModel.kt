package com.kdroid.kmplog.client.core.presentation

import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.core.data.network.WebSocketManager
import com.kdroid.kmplog.client.presentation.uimessagetoaster.UiMessageToasterViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val webSocketManager: WebSocketManager) : UiMessageToasterViewModel() {
    val isConnected get() = webSocketManager.isConnected
    private var wasConnectedPreviously: Boolean = false

    init {
        observeConnectionStatus()
        webSocketManager.startWebSocket()

    }

    private fun observeConnectionStatus() {
        viewModelScope.launch {
            isConnected.collect { connected ->
                if (connected) {
                    displayConnectedToast()
                    wasConnectedPreviously = true
                } else if (wasConnectedPreviously) {
                    displayDisconnectedToast()
                    wasConnectedPreviously = false
                }
            }
        }
    }

}
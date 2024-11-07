package com.kdroid.kmplog.client.core.presentation

import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.core.data.network.WebSocketManager
import com.kdroid.kmplog.client.core.presentation.uimessagetoaster.UiMessageToasterViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val webSocketManager: WebSocketManager) : UiMessageToasterViewModel() {
    val isConnected get() = webSocketManager.isConnected
    private var wasConnectedPreviously: Boolean = false

    init {
        observeConnectionStatus()
        webSocketManager.startWebSocket()

    }
    fun onEvent(events: MainEvents) {
        when (events) {
            is MainEvents.removeUiMessageById -> removeUiMessageById(events.id)
            MainEvents.openGithubPage -> openGithubPage()
        }
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
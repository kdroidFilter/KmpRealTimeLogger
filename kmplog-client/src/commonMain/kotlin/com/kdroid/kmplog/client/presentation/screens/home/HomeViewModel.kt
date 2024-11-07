package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.core.data.local.HomeSettingsEventDispatcher
import com.kdroid.kmplog.client.core.data.network.WebSocketManager
import com.kdroid.kmplog.client.domain.HomePreferencesRepository
import com.kdroid.kmplog.client.presentation.navigation.Navigator
import com.kdroid.kmplog.client.presentation.screens.settings.SettingsEvent
import com.kdroid.kmplog.client.presentation.uimessagetoaster.UiMessageToasterViewModel
import com.kdroid.kmplog.core.LogMessage
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(
    engine: HttpClientEngine,
    private val navigator: Navigator,
    private val repository: HomePreferencesRepository,
    private val webSocketManager: WebSocketManager
) : UiMessageToasterViewModel() {

    private val client = HttpClient(engine) {
        install(WebSockets)
    }


    private val _isSettingsVisible = MutableStateFlow(false)
    val isSettingsVisible: StateFlow<Boolean> get() = _isSettingsVisible

    private val _messages = mutableStateListOf<LogMessage>()
    val logMessages: List<LogMessage> get() = _messages

    val isConnected: StateFlow<Boolean> get() = webSocketManager.isConnected
    private var wasConnectedPreviously: Boolean = false

    private var _fontSize = MutableStateFlow(repository.getFontSize())
    val fontSize = _fontSize.asStateFlow()

    private fun observeMessages() {
        viewModelScope.launch {
            webSocketManager.messages.collect { message ->
                _messages.add(message)
            }
        }
    }

    private fun observeConnection() {
        viewModelScope.launch {
            isConnected.collect { connected ->
                if (connected) {
                    connected()
                    wasConnectedPreviously = true
                } else if (wasConnectedPreviously) {
                    disconnected()
                    wasConnectedPreviously = false
                }
            }
        }
    }

    private fun navigateToSettings() {
        viewModelScope.launch {
            navigateToSettings(navigator)
        }
    }

    fun onEvent(events: HomeEvents) {
        when (events) {
            HomeEvents.clearLogs -> _messages.clear()
            HomeEvents.onResetZoom -> setFontSize(14)
            is HomeEvents.onSearch -> TODO()
            is HomeEvents.onSearchClear -> TODO()
            HomeEvents.zoomIn -> incrementFontSize()
            HomeEvents.zoomOut -> decrementFontSize()
            HomeEvents.onSettingsClick -> {
                navigateToSettings()
                _isSettingsVisible.value = true
            }

            is HomeEvents.removeUiMessageById -> removeUiMessageById(events.id)
            HomeEvents.OnCloseSettings -> {
                viewModelScope.launch {
                    HomeSettingsEventDispatcher.emit(SettingsEvent.OnCloseSettings)
                    _isSettingsVisible.value = false

                }
            }
        }
    }

    private fun setFontSize(size: Int) {
        _fontSize.value = size
        repository.saveFontSize(size)
    }

    private fun incrementFontSize() {
        setFontSize(_fontSize.value + 1)
    }

    private fun decrementFontSize() {
        setFontSize(_fontSize.value - 1)
    }


    override fun onCleared() {
        super.onCleared()
        client.close()
    }

    init {
        webSocketManager.startWebSocket()
        observeMessages()
        observeConnection()
    }
}

expect suspend fun navigateToSettings(navigator: Navigator)
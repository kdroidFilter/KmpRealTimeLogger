package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.data.network.WebSocketManager
import com.kdroid.kmplog.client.domain.PreferencesRepository
import com.kdroid.kmplog.client.presentation.navigation.Destination
import com.kdroid.kmplog.client.presentation.navigation.Navigator
import com.kdroid.kmplog.client.presentation.toast.ToastViewModel
import com.kdroid.kmplog.core.LogMessage
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(engine: HttpClientEngine, private val navigator: Navigator, private val preferencesRepository: PreferencesRepository) : ToastViewModel() {

    private val client = HttpClient(engine) {
        install(WebSockets)
    }

    private val _messages = mutableStateListOf<LogMessage>()
    val logMessages: List<LogMessage> get() = _messages

    val isConnected: StateFlow<Boolean> get() = WebSocketManager.isConnected

    private var _fontSize = MutableStateFlow(preferencesRepository.getFontSize())
    val fontSize = _fontSize.asStateFlow()

    private fun observeMessages() {
        viewModelScope.launch {
            WebSocketManager.messages.collect { message ->
                _messages.add(message)
            }
        }
    }

    private fun observeConnection() {
        viewModelScope.launch {
            isConnected.collect { connected ->
                if (!connected) {
                    loadToFailure()
                } else {
                    loadToSuccess()
                }
            }
        }
    }

    fun navigateToSettings() {
        viewModelScope.launch {
            navigator.navigate(Destination.Settings)
        }
    }

    fun onEvent(events: HomeEvents) {
        when (events) {
            HomeEvents.clearLogs -> _messages.clear()
            HomeEvents.onResetZoom ->  setFontSize(14)
            is HomeEvents.onSearch -> TODO()
            is HomeEvents.onSearchClear -> TODO()
            HomeEvents.zoomIn -> incrementFontSize()
            HomeEvents.zoomOut -> decrementFontSize()
            HomeEvents.onSettingsClick -> navigateToSettings()
            is HomeEvents.removeUiMessageById -> removeUiMessageById(events.id)
        }
    }

    private fun setFontSize(size: Int) {
        _fontSize.value = size
        preferencesRepository.saveFontSize(size) // Sauvegarde automatique
    }

    // Incrémente la taille de police
    private fun incrementFontSize() {
        setFontSize(_fontSize.value + 1)
    }

    // Décrémente la taille de police
    private fun decrementFontSize() {
        setFontSize(_fontSize.value - 1)
    }


    override fun onCleared() {
        super.onCleared()
        client.close()
    }

    init {
        WebSocketManager.startWebSocket()
        observeMessages()
        observeConnection()
    }
}

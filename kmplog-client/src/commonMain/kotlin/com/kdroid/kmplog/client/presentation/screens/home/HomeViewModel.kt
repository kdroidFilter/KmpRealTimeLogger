package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.data.network.getIpService
import com.kdroid.kmplog.client.domain.PreferencesRepository
import com.kdroid.kmplog.client.presentation.navigation.Destination
import com.kdroid.kmplog.client.presentation.navigation.Navigator
import com.kdroid.kmplog.core.LogMessage
import com.kdroid.kmplog.core.SERVICE_PATH
import com.kdroid.kmplog.core.SERVICE_PORT
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf


class HomeViewModel(engine: HttpClientEngine, private val navigator: Navigator, private val preferencesRepository: PreferencesRepository) : ViewModel() {

    init {
        startWebSocket()
    }

    private val client = HttpClient(engine) {
        install(WebSockets)
    }

    private val _messages = mutableStateListOf<LogMessage>()
    val logMessages: List<LogMessage> get() = _messages

    private var _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    private var _fontSize = MutableStateFlow(preferencesRepository.getFontSize())
    val fontSize = _fontSize.asStateFlow()


    @OptIn(ExperimentalSerializationApi::class)
    private fun startWebSocket() {
        viewModelScope.launch {
            val host = getIpService()
            while (true) {
                try {
                    client.webSocket(
                        method = HttpMethod.Get,
                        host = host,
                        port = SERVICE_PORT,
                        path = SERVICE_PATH
                    ) {
                        _isConnected.value = true
                        for (frame in incoming) {
                            if (frame is Frame.Binary) {
                                try {
                                    val logMessage = ProtoBuf.decodeFromByteArray<LogMessage>(frame.readBytes())
                                    _messages.add(logMessage)
                                } catch (e: Exception) {
                                    println(e.message)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    _isConnected.value = false
                    delay(3000)
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
}

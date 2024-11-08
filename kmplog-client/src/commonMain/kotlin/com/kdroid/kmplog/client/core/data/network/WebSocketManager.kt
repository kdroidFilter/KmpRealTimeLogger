package com.kdroid.kmplog.client.core.data.network

import com.kdroid.kmplog.client.core.domain.repository.SettingsPreferencesRepository
import com.kdroid.kmplog.core.LogMessage
import com.kdroid.kmplog.core.SERVICE_PATH
import com.kdroid.kmplog.core.DEFAULT_SERVICE_PORT
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf

class WebSocketManager(
    private val settingsRepository: SettingsPreferencesRepository
) {
    private val client = HttpClient(engine) {
        install(WebSockets)
    }

    private var isWebSocketStarted = false


    private var _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    private val _messages = MutableSharedFlow<LogMessage>()
    val messages: SharedFlow<LogMessage> get() = _messages

    @OptIn(ExperimentalSerializationApi::class)
    fun startWebSocket() {
        if (isWebSocketStarted) return
        isWebSocketStarted = true
        CoroutineScope(Dispatchers.Default).launch {
            val isAutoDetection = settingsRepository.getAutomaticDetectionState()
            val host = if (isAutoDetection) getIpService() else settingsRepository.getCustomIpAddress("localhost")
            val port = if (isAutoDetection) DEFAULT_SERVICE_PORT else settingsRepository.getCustomPort().toInt()

            while (true) {
                try {
                    client.webSocket(
                        method = HttpMethod.Get,
                        host = host,
                        port = port,
                        path = SERVICE_PATH
                    ) {
                        _isConnected.value = true
                        for (frame in incoming) {
                            if (frame is Frame.Binary) {
                                val logMessage = ProtoBuf.decodeFromByteArray<LogMessage>(frame.readBytes())
                                _messages.emit(logMessage)
                            }
                        }
                    }
                } catch (e: Exception) {
                    _isConnected.value = false
                    delay(1000)
                }
            }
        }
    }

    fun closeConnection() {
        client.close()
        _isConnected.value = false
    }

    fun restartWebSocket() {
        if (isWebSocketStarted) {
            closeConnection()
            isWebSocketStarted = false
        }
        startWebSocket()
    }

}

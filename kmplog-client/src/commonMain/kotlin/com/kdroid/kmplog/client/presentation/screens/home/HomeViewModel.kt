package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.data.network.getIpService
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


class HomeViewModel(engine: HttpClientEngine) : ViewModel() {
    private val client = HttpClient(engine) {
        install(WebSockets)
    }

    private val _messages = mutableStateListOf<LogMessage>()
    val logMessages: List<LogMessage> get() = _messages

    init {
        startWebSocket()
    }

    private var _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()


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

    fun onEvent(events: HomeEvents) {
        when (events) {
            HomeEvents.onClearLogs -> TODO()
            HomeEvents.onResetZoom -> TODO()
            is HomeEvents.onSearch -> TODO()
            is HomeEvents.onSearchClear -> TODO()
            HomeEvents.onZoomIn -> TODO()
            HomeEvents.onZoomOut -> TODO()
        }
    }


    override fun onCleared() {
        super.onCleared()
        client.close()
    }
}

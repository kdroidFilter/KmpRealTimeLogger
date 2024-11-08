package com.kdroid.kmplog.websocket

import com.kdroid.kmplog.core.DEFAULT_SERVICE_PORT
import com.kdroid.kmplog.core.LogMessage
import com.kdroid.kmplog.core.SERVER_PATH
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf


@OptIn(ExperimentalSerializationApi::class)
val client = HttpClient(engine) {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(ProtoBuf)
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun sendMessageToWebSocket(logMessage: LogMessage) {
    GlobalScope.launch {
        try {
            client.webSocket(
                method = HttpMethod.Get,
                host = "0.0.0.0",
                port = DEFAULT_SERVICE_PORT,
                path = SERVER_PATH
            ) {
                sendSerialized(logMessage)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
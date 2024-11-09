package com.kdroid.kmplog.websocket

import com.kdroid.kmplog.core.DEFAULT_SERVICE_PORT
import com.kdroid.kmplog.core.LogMessage
import com.kdroid.kmplog.core.SERVER_PATH
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.websocket.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf

internal object WebSocketService {
    private var cachedIp: String? = null
    private var session: DefaultClientWebSocketSession? = null
    private var customIp: String? = null
    private var customPort: Int? = null


    suspend fun getCachedIp(): String? {
        // Use custom IP if defined, otherwise use default IP
        if (customIp != null) return customIp

        // If the IP is already cached, we return it directly
        if (cachedIp != null) return cachedIp

        // Otherwise, we perform an IP search
        cachedIp = getIpOfWebSocketService()
        return cachedIp
    }

    fun clearCachedIp() {
        // To force a new IP lookup we can call this function
        cachedIp = null
    }

    fun setCustomConnectionParams(ip: String?, port: Int?) {
        customIp = ip
        customPort = port
    }

    @OptIn(ExperimentalSerializationApi::class)
    val client = HttpClient(engine) {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(ProtoBuf)
        }
    }

    suspend fun initWebSocketConnection() {
        if (session == null || session?.isActive == false) {
            val ipAddress = getCachedIp()
            if (ipAddress != null) {
                try {
                    session = client.webSocketSession(
                        method = HttpMethod.Get,
                        host = ipAddress,
                        port = customPort ?: DEFAULT_SERVICE_PORT,
                        path = SERVER_PATH
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    suspend fun sendMessageToWebSocket(logMessage: LogMessage) {
        if (session == null || session?.isActive == false) {
            initWebSocketConnection()
        }
        session?.let {
            try {
                it.sendSerialized(logMessage)
            } catch (e: Exception) {
              //  e.printStackTrace()
                // If an error occurs, the session is closed to allow a new connection attempt
                session?.close()
                session = null
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun sendMessageToWebSocket(logMessage: LogMessage) {
    GlobalScope.launch {
        WebSocketService.sendMessageToWebSocket(logMessage)
    }
}

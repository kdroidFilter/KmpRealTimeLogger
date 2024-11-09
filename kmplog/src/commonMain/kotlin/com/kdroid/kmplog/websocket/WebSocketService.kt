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

    suspend fun getCachedIp(): String? {
        // Si l'IP est déjà en cache, on la retourne directement
        if (cachedIp != null) return cachedIp

        // Sinon, on effectue une recherche d'IP
        cachedIp = getIpOfWebSocketService()
        return cachedIp
    }

    fun clearCachedIp() {
        // Pour forcer une nouvelle recherche de l'IP, on peut appeler cette fonction
        cachedIp = null
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
                        port = DEFAULT_SERVICE_PORT,
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
                e.printStackTrace()
                // Si une erreur survient, on ferme la session pour permettre une nouvelle tentative de connexion
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

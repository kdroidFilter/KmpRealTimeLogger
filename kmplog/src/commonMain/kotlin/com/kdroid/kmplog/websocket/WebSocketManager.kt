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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf

object WebSocketService {
    private var cachedIp: String? = null

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
}

@OptIn(ExperimentalSerializationApi::class)
val client = HttpClient(engine) {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(ProtoBuf)
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun sendMessageToWebSocket(logMessage: LogMessage) {
    GlobalScope.launch {
        var ipFound = false
        while (!ipFound) {
            val ipAddress = WebSocketService.getCachedIp()
            if (ipAddress != null) { // On vérifie si une IP a été trouvée
                ipFound = true // On arrête de chercher
                try {
                    client.webSocket(
                        method = HttpMethod.Get,
                        host = ipAddress,
                        port = DEFAULT_SERVICE_PORT,
                        path = SERVER_PATH
                    ) {
                        sendSerialized(logMessage)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                delay(1000L) // On attend 1 seconde avant de réessayer
            }
        }
    }
}
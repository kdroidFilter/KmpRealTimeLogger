@file:OptIn(DelicateCoroutinesApi::class, ExperimentalSerializationApi::class)

package com.kdroid.kmplog

import com.kdroid.kmplog.core.LogMessage
import io.ktor.serialization.kotlinx.protobuf.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import kotlin.time.Duration.Companion.seconds


// Variables globales pour gérer les connexions WebSocket
internal var webSocketChannel: SendChannel<Frame>? = null
internal val connections = mutableSetOf<DefaultWebSocketServerSession>()
internal val mutex = Mutex()

actual fun startServer() {
    GlobalScope.launch {
        embeddedServer(CIO, port = 8080, host = "0.0.0.0") {

            install(CORS) {
                anyHost()
            }

            install(ContentNegotiation) {
                protobuf(ProtoBuf {
                    encodeDefaults = true
                })
            }

            install(WebSockets) {
                pingPeriod = 15.seconds
                timeout = 15.seconds
                maxFrameSize = Long.MAX_VALUE
                masking = false
            }

            routing {
                webSocket("/log") {
                    webSocketChannel = this.outgoing

                    // Ajouter la nouvelle connexion au set
                    mutex.withLock {
                        connections += this
                    }

                    try {
                        // Écouter les frames entrants
                        for (frame in incoming) {
                            if (frame is Frame.Binary) {
                                // Désérialiser le message Protobuf
                                val logMessage = ProtoBuf.decodeFromByteArray<LogMessage>(frame.readBytes())

                                // Diffuser le message reçu à tous les clients connectés
                                mutex.withLock {
                                    connections.forEach { session ->
                                        launch {
                                            try {
                                                // Sérialiser le message en Protobuf pour diffusion
                                                val serializedMessage = ProtoBuf.encodeToByteArray(logMessage)
                                                session.outgoing.send(Frame.Binary(true, serializedMessage))
                                            } catch (e: Exception) {
                                                // Gérer les exceptions d'envoi
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: ClosedReceiveChannelException) {
                        // Client déconnecté
                    } catch (e: Exception) {
                        // Gérer d'autres exceptions
                    } finally {
                        // Supprimer la connexion lorsqu'elle se ferme
                        mutex.withLock {
                            connections -= this
                        }
                    }
                }
            }
        }.start(wait = false)
    }
}

// Fonction pour envoyer un message en Protobuf à tous les clients WebSocket connectés
@OptIn(ExperimentalSerializationApi::class)
actual suspend fun sendMessageToWebSocket(logMessage: LogMessage) {
    val serializedMessage = ProtoBuf.encodeToByteArray(logMessage)
    mutex.withLock {
        connections.forEach { session ->
            try {
                session.outgoing.send(Frame.Binary(true, serializedMessage))
            } catch (e: Exception) {
                // Gérer les exceptions d'envoi
            }
        }
    }
}



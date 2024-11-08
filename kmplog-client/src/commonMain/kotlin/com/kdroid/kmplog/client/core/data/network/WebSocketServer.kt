package com.kdroid.kmplog.client.core.data.network

import com.kdroid.kmplog.core.DEFAULT_SERVICE_PORT
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import kotlin.time.Duration.Companion.seconds

// Global variables to manage WebSocket connections
internal var webSocketChannel: SendChannel<Frame>? = null
internal val connections = mutableSetOf<DefaultWebSocketServerSession>()
internal val mutex = Mutex()

// Global flow to emit received LogMessages
val logMessagesFlow = MutableSharedFlow<LogMessage>()

@OptIn(ExperimentalSerializationApi::class, DelicateCoroutinesApi::class)
fun startServer(port : Int = DEFAULT_SERVICE_PORT) {
    GlobalScope.launch {
        embeddedServer(CIO, port = port, host = "0.0.0.0") {

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

                    // Add the new connection to the set
                    mutex.withLock {
                        connections += this
                    }

                    try {
                        // Listen for incoming frames
                        for (frame in incoming) {
                            if (frame is Frame.Binary) {
                                // Deserialize the Protobuf message
                                val logMessage = ProtoBuf.decodeFromByteArray<LogMessage>(frame.readBytes())

                                // Emit the message to the flow
                                logMessagesFlow.emit(logMessage)

                                // Broadcast the received message to all connected clients
                                mutex.withLock {
                                    connections.forEach { session ->
                                        launch {
                                            try {
                                                // Serialize the message to Protobuf for broadcast
                                                val serializedMessage = ProtoBuf.encodeToByteArray(logMessage)
                                                session.outgoing.send(Frame.Binary(true, serializedMessage))
                                            } catch (e: Exception) {
                                                // TODO Handle sending exceptions
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: ClosedReceiveChannelException) {
                        // Client disconnected
                    } catch (e: Exception) {
                        // Handle other exceptions
                    } finally {
                        // Remove the connection when it closes
                        mutex.withLock {
                            connections -= this
                        }
                    }
                }
            }
        }.start(wait = false)
    }
}
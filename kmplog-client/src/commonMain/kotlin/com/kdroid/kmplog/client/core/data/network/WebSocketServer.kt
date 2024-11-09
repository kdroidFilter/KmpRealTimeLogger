package com.kdroid.kmplog.client.core.data.network

import com.kdroid.kmplog.core.DEFAULT_SERVICE_PORT
import com.kdroid.kmplog.core.LogMessage
import com.kdroid.kmplog.core.SERVER_PATH
import io.ktor.serialization.kotlinx.protobuf.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import kotlin.time.Duration.Companion.seconds

private val connections = mutableSetOf<DefaultWebSocketServerSession>()
private val mutex = Mutex()
val connectionStatus = MutableStateFlow(false)
val logMessagesFlow = MutableSharedFlow<LogMessage>()

@OptIn(ExperimentalSerializationApi::class)
fun startServer(port: Int = DEFAULT_SERVICE_PORT) {
    CoroutineScope(Dispatchers.IO).launch {
        embeddedServer(CIO, port = port, host = "0.0.0.0") {

            install(CORS) {
                anyHost()
            }

            install(ContentNegotiation) {
                protobuf()
            }

            install(WebSockets) {
                pingPeriod = 15.seconds
                timeout = 15.seconds
                maxFrameSize = Long.MAX_VALUE
                masking = false
            }

            routing {
                webSocket(SERVER_PATH) {
                    mutex.withLock {
                        connections += this
                        updateConnectionStatus()
                        println("New connection: \${this.call.request.origin.remoteHost}")
                    }

                    try {
                        for (frame in incoming) {
                            if (frame is Frame.Binary) {
                                try {
                                    val logMessage = ProtoBuf.decodeFromByteArray<LogMessage>(frame.readBytes())
                                    logMessagesFlow.emit(logMessage)
                                } catch (e: Exception) {
                                    println("Error during message deserialization: \${e.message}")
                                    continue
                                }
                            }
                        }
                        println("The 'incoming' loop ended normally.")
                    } catch (e: ClosedReceiveChannelException) {
                        println("Client disconnected: \${e.message}")
                    } catch (e: Exception) {
                        println("General exception: \${e.message}")
                    } finally {
                        mutex.withLock {
                            connections -= this
                            updateConnectionStatus()
                            println("Connection closed: \${this.call.request.origin.remoteHost}")
                        }
                    }
                }
            }
        }.start(wait = false)
    }
}

private fun updateConnectionStatus() {
    connectionStatus.value = connections.isNotEmpty()
}

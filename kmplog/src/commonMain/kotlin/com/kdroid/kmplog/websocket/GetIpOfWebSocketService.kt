package com.kdroid.kmplog.websocket

import io.ktor.client.engine.*

expect suspend fun getIpOfWebSocketService() : String?

expect val engine : HttpClientEngine

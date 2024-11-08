package com.kdroid.kmplog.websocket

import io.ktor.client.engine.*

expect suspend fun getIpService() : String?

expect val engine : HttpClientEngine

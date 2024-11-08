package com.kdroid.kmplog.websocket

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*

actual val engine: HttpClientEngine
    get() = CIO.create()
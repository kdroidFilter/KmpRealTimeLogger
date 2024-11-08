package com.kdroid.kmplog.websocket

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*

actual val engine: HttpClientEngine
    get() = Js.create()
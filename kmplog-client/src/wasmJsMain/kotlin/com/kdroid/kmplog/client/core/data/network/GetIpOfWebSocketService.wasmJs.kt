package com.kdroid.kmplog.client.core.data.network

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams

actual val engine: HttpClientEngine
    get() = Js.create()

actual suspend fun getIpService(): String? {
    return URLSearchParams(window.location.search.toJsString()).get("host")
}


package com.kdroid.kmplog.client.data.network

import com.kdroid.kmplog.core.SERVICE_NAME
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams

actual suspend fun getIpService(context: Any?): String? {
    return URLSearchParams(window.location.search.toJsString()).get(SERVICE_NAME)
}


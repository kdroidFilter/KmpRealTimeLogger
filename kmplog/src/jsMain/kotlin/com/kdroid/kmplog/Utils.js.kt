package com.kdroid.kmplog

import com.kdroid.kmplog.core.SERVICE_NAME
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams

actual fun getLocalIpAddress(): String? {
    return URLSearchParams(window.location.search).get(SERVICE_NAME)
}

internal fun isDarkMode(): Boolean {
    return window.matchMedia("(prefers-color-scheme: dark)").matches
}
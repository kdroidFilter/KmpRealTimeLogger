package com.kdroid.kmplog

import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams

actual fun getLocalIpAddress(): String? {
    return URLSearchParams(window.location.search.toJsString()).get("localIp")
}
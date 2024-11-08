package com.kdroid.kmplog

import com.kdroid.kmplog.core.SERVICE_NAME
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams



internal fun isDarkMode(): Boolean {
    return window.matchMedia("(prefers-color-scheme: dark)").matches
}
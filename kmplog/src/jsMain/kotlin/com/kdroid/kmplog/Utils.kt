package com.kdroid.kmplog

import kotlinx.browser.window

internal fun isDarkMode(): Boolean {
    return window.matchMedia("(prefers-color-scheme: dark)").matches
}
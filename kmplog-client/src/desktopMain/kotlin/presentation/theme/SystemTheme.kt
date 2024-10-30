package com.kdroid.kmplog.client.presentation.theme

import com.jthemedetecor.OsThemeDetector

object SystemTheme {
    private val detector = OsThemeDetector.getDetector()

    val isSystemeDarkTheme: Boolean
        get() = detector.isDark

    fun registerListener(listener: (Boolean) -> Unit) {
        detector.registerListener { isDark -> listener(isDark) }
    }
}
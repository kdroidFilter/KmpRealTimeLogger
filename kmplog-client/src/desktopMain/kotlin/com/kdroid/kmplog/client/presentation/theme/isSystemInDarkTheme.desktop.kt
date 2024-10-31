package com.kdroid.kmplog.client.presentation.theme

import androidx.compose.runtime.*
import com.jthemedetecor.OsThemeDetector

object SystemTheme {
    val detector = OsThemeDetector.getDetector()

    val isSystemeDarkTheme: Boolean
        get() = detector.isDark

    fun registerListener(listener: (Boolean) -> Unit) {
        detector.registerListener { isDark -> listener(isDark) }
    }
}

@Composable
actual fun isSystemInDarkTheme(): Boolean {
    var isDarkTheme by remember { mutableStateOf(SystemTheme.isSystemeDarkTheme) }
    DisposableEffect(Unit) {
        val themeListener: (Boolean) -> Unit = { isDark ->
            isDarkTheme = isDark // Met à jour l'état en fonction du changement de thème
        }

        SystemTheme.registerListener(themeListener)

        onDispose {
            SystemTheme.detector.removeListener(themeListener)
        }
    }
    return isDarkTheme
}
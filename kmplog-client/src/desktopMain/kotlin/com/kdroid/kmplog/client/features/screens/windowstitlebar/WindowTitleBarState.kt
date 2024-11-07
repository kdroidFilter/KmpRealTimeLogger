package com.kdroid.kmplog.client.features.screens.windowstitlebar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.kdroid.kmplog.client.core.presentation.MainViewModel

data class WindowTitleBarState(
    val isConnected: Boolean
) {
    companion object {
        val preview = WindowTitleBarState(
            isConnected = true
        )
    }
}

@Composable
fun rememberWindowTitleBarState(mainViewModel: MainViewModel): WindowTitleBarState {
    return WindowTitleBarState(mainViewModel.isConnected.collectAsState().value)
}

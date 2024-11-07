package com.kdroid.kmplog.client.features.screens.windowstitlebar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.kdroid.kmplog.client.core.presentation.MainViewModel

data class AppTitleBarState(
    val isConnected: Boolean
) {
    companion object {
        val preview = AppTitleBarState(
            isConnected = true
        )
    }
}

@Composable
fun rememberAppTitleBarState(mainViewModel: MainViewModel): AppTitleBarState {
    return AppTitleBarState(mainViewModel.isConnected.collectAsState().value)
}

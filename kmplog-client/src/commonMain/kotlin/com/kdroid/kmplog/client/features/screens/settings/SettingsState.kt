package com.kdroid.kmplog.client.features.screens.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

data class SettingsState(

    val customPort: String,
) {
    companion object {
        val preview = SettingsState(
            customPort = ""
        )
    }
}

@Composable
fun rememberSettingsState(vm : SettingsViewModel) : SettingsState {
    return SettingsState(
        customPort = vm.customPort.collectAsStateWithLifecycle().value,
    )
}
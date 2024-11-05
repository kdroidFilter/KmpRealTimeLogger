package com.kdroid.kmplog.client.presentation.screens.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

data class SettingsState(
    val automaticDetection: Boolean,
    val customIpAdress: String,
    val customPort: String,
) {
    companion object {
        val preview = SettingsState(
            automaticDetection = true,
            customIpAdress = "",
            customPort = ""
        )
    }
}

@Composable
fun rememberSettingsState(vm : SettingsViewModel) : SettingsState {
    return SettingsState(
        automaticDetection = vm.autoDetection.collectAsStateWithLifecycle().value,
        customIpAdress = vm.customIp.collectAsStateWithLifecycle().value,
        customPort = vm.customPort.collectAsStateWithLifecycle().value,
    )
}
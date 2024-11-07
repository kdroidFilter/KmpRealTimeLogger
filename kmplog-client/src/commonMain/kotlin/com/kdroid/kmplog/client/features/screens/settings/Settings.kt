package com.kdroid.kmplog.client.features.screens.settings

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Settings() {
    val vm : SettingsViewModel = koinViewModel()
    SettingsScreen(state = rememberSettingsState(vm), onEvent = vm::onEvent)
}

@Composable
expect fun SettingsScreen(state: SettingsState, onEvent: (SettingsEvent) -> Unit)
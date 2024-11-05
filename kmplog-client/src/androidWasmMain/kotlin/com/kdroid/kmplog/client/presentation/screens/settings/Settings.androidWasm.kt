package com.kdroid.kmplog.client.presentation.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsSwitch
import com.kdroid.kmplog.client.kmplog_client.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun SettingsScreen(state: SettingsState, onEvent: (SettingsEvent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.settings))
                },
                navigationIcon = {
                    IconButton({
                        onEvent(SettingsEvent.OnBackClick)
                    }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Surface(modifier = Modifier.fillMaxSize().padding(paddingValues)) {


            Column{
                SettingsGroup(
                    enabled = true,
                    title = { Text(text = stringResource(Res.string.host_configuration)) },
                    contentPadding = PaddingValues(16.dp),
                ) {

                    SettingsSwitch(
                        state = state.automaticDetection,
                        title = { Text(text = stringResource(Res.string.automatic_detection)) },
                        onCheckedChange = { newState: Boolean -> onEvent(SettingsEvent.OnAutomaticDetectionChange(newState)) },
                    )
                    OutlinedTextField(
                        value = state.customIpAdress,
                        onValueChange = { onEvent(SettingsEvent.OnIpChange(it)) },
                        label = { Text(stringResource(Res.string.ip_address)) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        placeholder = { Text(stringResource(Res.string.ip_placeholder)) },
                        enabled = state.automaticDetection.not(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    OutlinedTextField(
                        value = state.customPort,
                        onValueChange = { onEvent(SettingsEvent.OnPortChange(it)) },
                        label = { Text(stringResource(Res.string.port)) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        enabled = state.automaticDetection.not(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }
        }
    }
}
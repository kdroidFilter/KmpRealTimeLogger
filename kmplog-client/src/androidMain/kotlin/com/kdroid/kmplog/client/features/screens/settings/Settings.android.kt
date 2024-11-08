package com.kdroid.kmplog.client.features.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alorma.compose.settings.ui.SettingsSwitch
import com.kdroid.kmplog.client.kmplog_client.generated.resources.*
import com.kdroid.kmplog.client.features.screens.home.appBarBackgroundColor
import com.kdroid.kmplog.client.core.presentation.theme.TOP_APP_BAR_HEIGHT
import com.kdroid.kmplog.client.core.presentation.theme.TOP_APP_BAR_TEXT_SIZE
import org.jetbrains.compose.resources.stringResource

@Composable
fun settingsBackgroundColor(): Color {
    return if (isSystemInDarkTheme()) Color.Black else Color.White
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun SettingsScreen(state: SettingsState, onEvent: (SettingsEvent) -> Unit) {
    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.settings), fontSize = TOP_APP_BAR_TEXT_SIZE)
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = appBarBackgroundColor()
                ),
                windowInsets = WindowInsets(0),
                expandedHeight = TOP_APP_BAR_HEIGHT,
                navigationIcon = {
                    IconButton({
                        onEvent(SettingsEvent.OnBackClick)
                    }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Back", modifier = Modifier.size(16.dp))
                    }
                },
            )
        }
    ) { paddingValues ->

        Surface(
            modifier = Modifier.fillMaxSize().padding(paddingValues), color = settingsBackgroundColor()
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                Text(text = stringResource(Res.string.host_configuration), fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    SettingsSwitch(
                        state = state.automaticDetection,
                        title = { Text(text = stringResource(Res.string.automatic_detection)) },
                        onCheckedChange = { newState: Boolean ->
                            onEvent(
                                SettingsEvent.OnAutomaticDetectionChange(
                                    newState
                                )
                            )
                        },
                        modifier = Modifier.background(
                            settingsBackgroundColor()
                        ),
                        colors = ListItemDefaults.colors(
                            containerColor = settingsBackgroundColor(),
                            overlineColor = settingsBackgroundColor()
                        )
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
                        onValueChange = { onEvent(SettingsEvent.OnPortChange(port = it)) },
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
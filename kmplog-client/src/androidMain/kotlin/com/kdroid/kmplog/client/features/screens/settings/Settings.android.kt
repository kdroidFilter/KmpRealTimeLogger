package com.kdroid.kmplog.client.features.screens.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdroid.kmplog.client.core.presentation.theme.TOP_APP_BAR_HEIGHT
import com.kdroid.kmplog.client.core.presentation.theme.TOP_APP_BAR_TEXT_SIZE
import com.kdroid.kmplog.client.features.screens.home.appBarBackgroundColor
import com.kdroid.kmplog.client.kmplog_client.generated.resources.*
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
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = settingsBackgroundColor()
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = stringResource(Res.string.server_configuration),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    stringResource(Res.string.restart_app_message),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    OutlinedTextField(
                        value = state.customPort,
                        onValueChange = { onEvent(SettingsEvent.OnPortChange(port = it)) },
                        label = { Text(stringResource(Res.string.port)) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        enabled = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }
        }

    }
}
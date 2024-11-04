package com.kdroid.kmplog.client.presentation.screens.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.kdroid.kmplog.client.kmplog_client.generated.resources.Res
import com.kdroid.kmplog.client.kmplog_client.generated.resources.settings
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun SettingsScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(Res.string.settings)) },
                navigationIcon = { IconButton({}){
                    Icon(Icons.AutoMirrored.Default.ArrowBack, "Back")
                } }
            )
        }
    ) {


    }
}
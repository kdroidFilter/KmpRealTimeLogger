package com.kdroid.kmplog.client

import androidx.compose.runtime.Composable
import com.kdroid.kmplog.client.core.data.network.publishMdnsService
import com.kdroid.kmplog.client.core.data.network.startServer
import com.kdroid.kmplog.client.core.domain.repository.SettingsPreferencesRepository
import com.kdroid.kmplog.client.core.presentation.navigation.MainNavHost
import com.kdroid.kmplog.client.core.presentation.theme.AppTheme
import org.koin.compose.KoinContext
import org.koin.java.KoinJavaComponent.inject


@Composable
fun App() {
    KoinContext {
        val settingsPreferencesRepository: SettingsPreferencesRepository by inject(SettingsPreferencesRepository::class.java)
        startServer(settingsPreferencesRepository)
        publishMdnsService(settingsPreferencesRepository)
       AppTheme {
           MainNavHost()
       }
    }
}



package com.kdroid.kmplog.client

import androidx.compose.runtime.Composable
import com.kdroid.kmplog.client.core.presentation.navigation.MainNavHost
import com.kdroid.kmplog.client.core.presentation.theme.AppTheme
import org.koin.compose.KoinContext


@Composable
fun App() {
    // MainNavHost()
    KoinContext {
       AppTheme {
           MainNavHost()
       }
    }
}



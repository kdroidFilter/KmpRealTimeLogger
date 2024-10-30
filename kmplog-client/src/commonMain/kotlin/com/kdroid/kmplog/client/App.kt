package com.kdroid.kmplog.client

import androidx.compose.runtime.Composable
import com.kdroid.kmplog.client.presentation.screens.home.HomeScreen
import com.kdroid.kmplog.client.presentation.screens.home.HomeViewModel
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun App() {
    // MainNavHost()
    KoinContext {
        val viewModel = koinViewModel<HomeViewModel>()
        HomeScreen(viewModel)
    }
}



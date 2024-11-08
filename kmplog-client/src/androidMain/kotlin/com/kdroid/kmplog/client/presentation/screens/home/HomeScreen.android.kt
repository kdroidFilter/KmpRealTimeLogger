package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.runtime.Composable
import com.kdroid.kmplog.client.features.screens.home.HomeScreen
import com.kdroid.kmplog.client.features.screens.home.HomeState

@Composable
fun HomeScreenPreview() {
    HomeScreen(homeState = HomeState.previewState, {}, {})
}



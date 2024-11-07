package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.kdroid.kmplog.client.features.screens.home.HomeScreen
import com.kdroid.kmplog.client.features.screens.home.HomeState

@PreviewLightDark
@Composable
fun HomeScreenPreview() {
    HomeScreen(homeState = HomeState.previewState, {})
}



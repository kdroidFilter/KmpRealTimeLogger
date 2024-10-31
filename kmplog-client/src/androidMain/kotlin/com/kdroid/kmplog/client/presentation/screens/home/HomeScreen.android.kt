package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes

@PreviewScreenSizes
@PreviewLightDark
@Composable
fun HomeScreenPreview() {
    HomeScreen(homeState = HomeState.previewState)
}
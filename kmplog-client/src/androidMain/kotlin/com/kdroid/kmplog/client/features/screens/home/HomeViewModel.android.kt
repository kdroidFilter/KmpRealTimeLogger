package com.kdroid.kmplog.client.features.screens.home

import com.kdroid.kmplog.client.core.presentation.navigation.Destination
import com.kdroid.kmplog.client.core.presentation.navigation.Navigator

actual suspend fun navigateToSettings(navigator: Navigator) {
    navigator.navigate(Destination.Settings)
}
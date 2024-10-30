package com.kdroid.kmplog.client.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Destination {

    @Serializable
    data object Home : Destination()

    @Serializable
    data object Settings : Destination()
}
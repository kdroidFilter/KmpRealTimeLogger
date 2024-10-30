package com.kdroid.kmplog.client.presentation.navigation

import kotlinx.serialization.Serializable

sealed class MainNavGraph {

    @Serializable
    data object Home : MainNavGraph()

    @Serializable
    data object Settings : MainNavGraph()
}
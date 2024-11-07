package com.kdroid.kmplog.client.core.presentation.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationAction {

    data class Navigate(
        val destination: Destination,
        val navOptions : NavOptionsBuilder.() -> Unit = {}
    ): NavigationAction

    data object NavigateUp : NavigationAction

}
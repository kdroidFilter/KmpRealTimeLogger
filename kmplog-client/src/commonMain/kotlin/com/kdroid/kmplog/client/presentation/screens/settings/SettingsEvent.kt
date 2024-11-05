package com.kdroid.kmplog.client.presentation.screens.settings

sealed class SettingsEvent {
    data object OnBackClick : SettingsEvent()
    data object OnCloseSettings : SettingsEvent()
    data class OnAutomaticDetectionChange(val automaticDetection: Boolean) : SettingsEvent()
    data class OnIpChange(val ip: String) : SettingsEvent()
    data class OnPortChange(val port: String) : SettingsEvent()
}
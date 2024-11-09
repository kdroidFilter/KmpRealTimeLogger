package com.kdroid.kmplog.client.features.screens.settings

sealed class SettingsEvent {
    data object OnBackClick : SettingsEvent()
    data object OnCloseSettings : SettingsEvent()
    data class OnPortChange(val port: String) : SettingsEvent()
}
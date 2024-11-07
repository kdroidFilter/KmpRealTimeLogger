package com.kdroid.kmplog.client.core.data.local

import com.kdroid.kmplog.client.features.screens.settings.SettingsEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object HomeSettingsEventDispatcher {
    private val _events = MutableSharedFlow<SettingsEvent>()
    val events = _events.asSharedFlow()

    suspend fun emit(event: SettingsEvent) {
        _events.emit(event)
    }
}
package com.kdroid.kmplog.client.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.domain.PreferencesRepository
import com.kdroid.kmplog.client.presentation.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val navigator: Navigator, private val preferencesRepository: PreferencesRepository) : ViewModel() {

    private var _autoDetection = MutableStateFlow(true)
    val autoDetection = _autoDetection.asStateFlow()
    private var _ip = MutableStateFlow("")
    val ip = _ip.asStateFlow()
    private var _port = MutableStateFlow("")
    val port = _port.asStateFlow()


    fun onEvent(events: SettingsEvent) {
        when (events) {
            is SettingsEvent.OnAutomaticDetectionChange -> {
                _autoDetection.value = events.automaticDetection
            }
            SettingsEvent.OnBackClick -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }
            is SettingsEvent.OnIpChange -> {
                _ip.value = events.ip
            }
            is SettingsEvent.OnPortChange -> {
                _port.value = events.port
            }
        }
    }

}
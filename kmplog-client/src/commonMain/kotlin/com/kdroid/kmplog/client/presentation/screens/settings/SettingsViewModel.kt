package com.kdroid.kmplog.client.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.core.HomeSettingsEventDispatcher
import com.kdroid.kmplog.client.domain.SettingsPreferencesRepository
import com.kdroid.kmplog.client.presentation.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val navigator: Navigator, private val repository: SettingsPreferencesRepository) : ViewModel() {

    private var _autoDetection = MutableStateFlow(repository.getAutomaticDetectionState())
    val autoDetection = _autoDetection.asStateFlow()
    private var _customIp = MutableStateFlow(repository.getCustomIpAddress(""))
    val customIp = _customIp.asStateFlow()
    private var _port = MutableStateFlow(repository.getCustomPort(""))
    val port = _port.asStateFlow()


    fun onEvent(events: SettingsEvent) {
        when (events) {
            is SettingsEvent.OnAutomaticDetectionChange -> {
                _autoDetection.value = events.automaticDetection
                repository.saveAutomaticDetectionState(events.automaticDetection)
            }
            SettingsEvent.OnBackClick -> {
                saveSettings()
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }
            is SettingsEvent.OnIpChange -> {
                _customIp.value = events.ip
            }
            is SettingsEvent.OnPortChange -> {
                _port.value = events.port
            }

            SettingsEvent.OnCloseSettings -> {
                saveSettings()
            }
        }
    }

    private fun saveSettings() {
        repository.saveCustomIpAddress(_customIp.value)
        repository.saveCustomPort(_port.value)
    }

    init {
        viewModelScope.launch {
            HomeSettingsEventDispatcher.events.collect { event ->
                when (event) {
                    SettingsEvent.OnCloseSettings -> {
                        saveSettings()
                    }
                    else -> Unit
                }
            }
        }
    }

}
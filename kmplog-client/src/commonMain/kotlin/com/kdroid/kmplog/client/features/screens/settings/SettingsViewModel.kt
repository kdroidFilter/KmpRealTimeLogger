package com.kdroid.kmplog.client.features.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdroid.kmplog.client.core.data.local.HomeSettingsEventDispatcher
import com.kdroid.kmplog.client.core.domain.repository.SettingsPreferencesRepository
import com.kdroid.kmplog.client.core.presentation.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val navigator: Navigator, private val repository: SettingsPreferencesRepository) :
    ViewModel() {


    private var _customPort = MutableStateFlow(repository.getCustomPort())
    val customPort = _customPort.asStateFlow()

    init {
        viewModelScope.launch {
            HomeSettingsEventDispatcher.events.collect { event ->
                onEvent(event)
            }
        }
    }

    fun onEvent(events: SettingsEvent) {
        when (events) {

            SettingsEvent.OnBackClick -> {
                saveSettings()
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }

            is SettingsEvent.OnPortChange -> {
                _customPort.value = events.port
            }

            SettingsEvent.OnCloseSettings -> {
                saveSettings()
            }

        }
    }

    private fun resetSettings() {
        repository.saveCustomPort("")
        _customPort.value = ""
    }

    private fun saveSettings() {
        repository.saveCustomPort(_customPort.value)
    }


}
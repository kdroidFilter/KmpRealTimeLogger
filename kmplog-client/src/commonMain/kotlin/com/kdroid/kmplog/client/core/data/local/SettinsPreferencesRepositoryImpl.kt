package com.kdroid.kmplog.client.core.data.local

import com.kdroid.kmplog.client.core.domain.SettingsPreferencesRepository
import com.russhwolf.settings.Settings

class SettinsPreferencesRepositoryImpl(private val settings: Settings) : SettingsPreferencesRepository {

    private val autoDetectionKey = "auto_detection"
    private val customIpKey = "custom_ip"
    private val portKey = "port"

    override fun saveAutomaticDetectionState(state: Boolean) {
        settings.putBoolean(autoDetectionKey, state)
    }

    override fun getAutomaticDetectionState(defaultState: Boolean): Boolean {
        return settings.getBoolean(autoDetectionKey, defaultState)
    }

    override fun saveCustomIpAddress(ip: String) {
        settings.putString(customIpKey, ip)
    }

    override fun getCustomIpAddress(defaultIp: String): String {
        return settings.getString(customIpKey, defaultIp)
    }

    override fun saveCustomPort(port: String) {
       settings.putString(portKey, port)
    }

    override fun getCustomPort(defaultPort: String): String {
        return settings.getString(portKey, defaultPort)
    }


    override fun clearAll() {

    }

}
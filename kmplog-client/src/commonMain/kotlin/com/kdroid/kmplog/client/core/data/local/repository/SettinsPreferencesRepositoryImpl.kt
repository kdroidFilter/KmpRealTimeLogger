package com.kdroid.kmplog.client.core.data.local.repository

import com.kdroid.kmplog.client.core.domain.repository.SettingsPreferencesRepository
import com.russhwolf.settings.Settings

class SettinsPreferencesRepositoryImpl(private val settings: Settings) : SettingsPreferencesRepository {

    private val portKey = "port"

    override fun saveCustomPort(port: String) {
       settings.putString(portKey, port)
    }

    override fun getCustomPort(defaultPort: String): String {
        return settings.getString(portKey, defaultPort)
    }

    override fun clearAll() {

    }

}
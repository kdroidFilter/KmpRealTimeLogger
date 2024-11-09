package com.kdroid.kmplog.client.core.domain.repository

import com.kdroid.kmplog.core.DEFAULT_SERVICE_PORT

interface SettingsPreferencesRepository {

    fun saveCustomPort(port: String)
    fun getCustomPort(defaultPort: String = DEFAULT_SERVICE_PORT.toString()): String

    fun clearAll()
}
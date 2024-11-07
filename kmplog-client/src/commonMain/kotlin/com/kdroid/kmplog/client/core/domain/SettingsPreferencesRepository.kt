package com.kdroid.kmplog.client.core.domain

interface SettingsPreferencesRepository {
    fun saveAutomaticDetectionState(state: Boolean)
    fun getAutomaticDetectionState(defaultState: Boolean = true): Boolean

    fun saveCustomIpAddress(ip: String)
    fun getCustomIpAddress(defaultIp: String = ""): String

    fun saveCustomPort(port: String)
    fun getCustomPort(defaultPort: String = ""): String

    fun clearAll()
}
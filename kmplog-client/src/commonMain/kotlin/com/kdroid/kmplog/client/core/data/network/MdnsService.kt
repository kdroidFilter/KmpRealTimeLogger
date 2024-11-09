package com.kdroid.kmplog.client.core.data.network

import com.kdroid.kmplog.client.core.domain.repository.SettingsPreferencesRepository

expect fun getLocalIpAddress(): String?

expect fun publishMdnsService(repository: SettingsPreferencesRepository)
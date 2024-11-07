package com.kdroid.kmplog.client.core.data.network

import io.ktor.client.engine.*

expect suspend fun getIpService() : String?

expect val engine : HttpClientEngine

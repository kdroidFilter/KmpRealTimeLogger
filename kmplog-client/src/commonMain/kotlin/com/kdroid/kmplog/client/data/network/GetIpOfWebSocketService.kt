package com.kdroid.kmplog.client.data.network

import io.ktor.client.engine.*

expect suspend fun getIpService() : String?

expect val engine : HttpClientEngine

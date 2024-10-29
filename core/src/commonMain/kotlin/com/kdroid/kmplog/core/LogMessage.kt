package com.kdroid.kmplog.core

import kotlinx.serialization.Serializable

@Serializable
data class LogMessage(
    val priority: Int,
    val timestamp: String,
    val tag: String,
    val message: String
)
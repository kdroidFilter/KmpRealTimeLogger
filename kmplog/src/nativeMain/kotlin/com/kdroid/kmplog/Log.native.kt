@file:OptIn(DelicateCoroutinesApi::class)

package com.kdroid.kmplog

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


actual fun printAndSendLog(priority: Int, tag: String, msg: String) {
    val logMessage = printAndGetLocalLog(tag = tag, message = msg, priority = priority)
    GlobalScope.launch {
        sendMessageToWebSocket(logMessage)
    }
}
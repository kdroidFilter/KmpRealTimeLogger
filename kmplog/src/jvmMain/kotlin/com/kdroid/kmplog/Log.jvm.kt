package com.kdroid.kmplog

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


actual fun printAndSendLog(priority: Int, tag: String, msg: String) {
   val logMessage = printAndGetLocalLog(tag = tag, message = msg, priority = priority)
    CoroutineScope(Dispatchers.IO).launch {
        sendMessageToWebSocket(logMessage)
    }
}
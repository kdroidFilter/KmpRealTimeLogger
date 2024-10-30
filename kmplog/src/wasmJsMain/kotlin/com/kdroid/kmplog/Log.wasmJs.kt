package com.kdroid.kmplog

import com.kdroid.kmplog.core.LogMessage

//Wasm platform cannot start server
//TODO Implement server in the client side for wasm platform
actual fun startServer() {}
actual fun publishMdnsService() {}


actual suspend fun sendMessageToWebSocket(logMessage: LogMessage) {
}

actual fun printAndSendLog(priority: Int, tag: String, msg: String) {
    printAndGetLocalLog(tag = tag, message = msg, priority = priority)
}


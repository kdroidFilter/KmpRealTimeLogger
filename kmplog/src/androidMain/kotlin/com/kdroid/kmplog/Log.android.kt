package com.kdroid.kmplog

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import com.kdroid.androidcontextprovider.ContextProvider
import com.kdroid.kmplog.core.*
import com.kdroid.kmplog.websocket.sendMessageToWebSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

actual fun Log.v(tag: String, msg: String) {
    if (isLoggable(tag, VERBOSE)) {
        android.util.Log.v(tag, msg)
        sendLog(VERBOSE, tag, msg)
    }
}

actual fun Log.d(tag: String, msg: String) {
    if (isLoggable(tag, DEBUG)) {
        android.util.Log.d(tag, msg)
        sendLog(DEBUG, tag, msg)
    }
}

actual fun Log.i(tag: String, msg: String) {
    if (isLoggable(tag, INFO))  {
        android.util.Log.i(tag, msg)
        sendLog(INFO, tag, msg)
    }
}

actual fun Log.w(tag: String, msg: String) {
    if (isLoggable(tag, WARN)) {
        android.util.Log.w(tag, msg)
        sendLog(WARN, tag, msg)
    }
}

actual fun Log.e(tag: String, msg: String, throwable: Throwable?) {
    if (isLoggable(tag, ERROR)) {
        if (throwable != null) {
            android.util.Log.e(tag, msg, throwable)
            sendLog(ERROR, tag, msg + " " + throwable.localizedMessage)
        } else {
            android.util.Log.e(tag, msg)
            sendLog(ERROR, tag, msg)
        }
    }
}

actual fun Log.wtf(tag: String, msg: String) {
    if (isLoggable(tag, ASSERT)) {
        android.util.Log.wtf(tag, msg)
        sendLog(ASSERT, tag, msg)
    }
}

actual fun Log.println(priority: Int, tag: String, msg: String) {
    if (isLoggable(tag, priority)) android.util.Log.println(priority, tag, msg)
}

actual fun printAndSendLog(priority: Int, tag: String, msg: String) {

}

private fun sendLog(priority: Int, tag: String, msg: String, throwable: String? = null){
    CoroutineScope(Dispatchers.Main.immediate).launch {
        sendMessageToWebSocket(LogMessage(priority = priority, tag = tag, timestamp = getCurrentDateTime(), message = msg))
    }
}


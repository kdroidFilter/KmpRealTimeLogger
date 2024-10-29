package com.kdroid.kmplog

import com.kdroid.kmplog.core.*


actual fun Log.v(tag: String, msg: String) {
    if (isLoggable(tag, VERBOSE)) {
        printAndSendLog(VERBOSE, tag, msg)
    }

}

actual fun Log.d(tag: String, msg: String) {
    if (isLoggable(tag, DEBUG)) {
        printAndSendLog(DEBUG, tag, msg)
    }
}

actual fun Log.i(tag: String, msg: String) {
    if (isLoggable(tag, INFO)) {
        printAndSendLog(INFO, tag, msg)
    }
}

actual fun Log.w(tag: String, msg: String) {
    if (isLoggable(tag, WARN)) {
        printAndSendLog(WARN, tag, msg)
    }
}

actual fun Log.e(tag: String, msg: String, throwable: Throwable?) {
    if (isLoggable(tag, ERROR)) {
        val logMessage = if (throwable != null) {
            "$msg\n${throwable.stackTraceToString()}"
        } else {
            msg
        }
        printAndSendLog(ERROR, tag, logMessage)
    }
}

actual fun Log.wtf(tag: String, msg: String) {
    if (isLoggable(tag, ASSERT)) {
        printAndSendLog(ASSERT, tag, msg)
    }
}

actual fun Log.println(priority: Int, tag: String, msg: String) {
    if (isLoggable(tag, priority)) {
        printAndSendLog(priority, tag, msg)
    }
}



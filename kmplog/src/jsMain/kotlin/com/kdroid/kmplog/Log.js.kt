package com.kdroid.kmplog

import com.kdroid.kmplog.Log.ASSERT
import com.kdroid.kmplog.Log.DEBUG
import com.kdroid.kmplog.Log.ERROR
import com.kdroid.kmplog.Log.INFO
import com.kdroid.kmplog.Log.VERBOSE
import com.kdroid.kmplog.Log.WARN

private fun getColor(priority: Int): String {
    val isDark = isDarkMode()

    return when (priority) {
        VERBOSE -> if (isDark) "color: lightgray" else "color: gray"
        DEBUG -> if (isDark) "color: lightblue" else "color: blue"
        INFO -> if (isDark) "color: lightgreen" else "color: green"
        WARN -> if (isDark) "color: yellow" else "color: orange"
        ERROR -> if (isDark) "color: pink" else "color: red"
        ASSERT -> if (isDark) "color: lightmagenta" else "color: magenta"
        else -> if (isDark) "color: white" else "color: black"
    }
}

private fun printLog(priority: Int, tag: String, msg: String, throwable: Throwable? = null) {
    val style = getColor(priority)
    val priorityChar = getPriorityChar(priority)
    val timestamp = getCurrentDateTime()

    // Assemble the final message
    val throwableMsg = throwable?.let { "\n${it.stackTraceToString()}" } ?: ""
    val formattedMsg = "%c$timestamp  $tag  $priorityChar  $msg$throwableMsg"
    when (priority) {
        VERBOSE, DEBUG, INFO -> console.log(formattedMsg, style)
        WARN -> console.warn(formattedMsg, style)
        ERROR, ASSERT -> console.error(formattedMsg, style)
        else -> console.log(formattedMsg, style)
    }
}

actual fun Log.v(tag: String, msg: String) {
    if (isLoggable( tag, VERBOSE)) {
        printLog(VERBOSE, tag, msg)
    }
}

actual fun Log.d(tag: String, msg: String) {
    if (isLoggable( tag, DEBUG)) {
        printLog(DEBUG, tag, msg)
    }
}

actual fun Log.i(tag: String, msg: String) {
    if (isLoggable( tag, INFO)) {
        printLog(INFO, tag, msg)
    }
}

actual fun Log.w(tag: String, msg: String) {
    if (isLoggable( tag, WARN)) {
        printLog(WARN, tag, msg)
    }
}

actual fun Log.e(tag: String, msg: String, throwable: Throwable?) {
    if (isLoggable(tag, ERROR)) {
        printLog(ERROR, tag, msg, throwable)
    }
}

actual fun Log.wtf(tag: String, msg: String) {
    if (isLoggable( tag, ASSERT)) {
        printLog(ASSERT, tag, msg)
    }
}

actual fun Log.println(priority: Int, tag: String, msg: String) {
    if (isLoggable( tag, priority)) {
        printLog(priority, tag, msg)
    }
}

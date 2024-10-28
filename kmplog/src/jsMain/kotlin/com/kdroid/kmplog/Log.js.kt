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

private fun formatTag(tag: String, maxLength: Int = MAX_TAG_LENGTH): String {
    return if (tag.length > maxLength) {
        tag.substring(0, maxLength)
    } else {
        tag.padEnd(maxLength)
    }
}

private fun formatMessage(msg: String, maxLength: Int = MAX_MSG_LENGTH): String {
    return if (msg.length > maxLength) {
        msg.substring(0, maxLength - 3) + "..."
    } else {
        msg
    }
}

private fun printLog(priority: Int, tag: String, msg: String, throwable: Throwable? = null) {
    val style = getColor(priority)
    val priorityChar = getPriorityChar(priority)
    val timestamp = getCurrentDateTime()

    val formattedTag = formatTag(tag)
    val formattedMsg = formatMessage(msg)
    val throwableMsg = throwable?.let { "\n${it.stackTraceToString()}" } ?: ""

    // Assemble the final message with formatted tag and message
    val logMessage = "$timestamp  $formattedTag  $priorityChar  $formattedMsg$throwableMsg"
    val formattedLogMessage = "%c$logMessage"

    when (priority) {
        VERBOSE, DEBUG, INFO -> console.log(formattedLogMessage, style)
        WARN -> console.warn(formattedLogMessage, style)
        ERROR, ASSERT -> console.error(formattedLogMessage, style)
        else -> console.log(formattedLogMessage, style)
    }
}

actual fun Log.v(tag: String, msg: String) {
    if (isLoggable(tag, VERBOSE)) {
        printLog(VERBOSE, tag, msg)
    }
}

actual fun Log.d(tag: String, msg: String) {
    if (isLoggable(tag, DEBUG)) {
        printLog(DEBUG, tag, msg)
    }
}

actual fun Log.i(tag: String, msg: String) {
    if (isLoggable(tag, INFO)) {
        printLog(INFO, tag, msg)
    }
}

actual fun Log.w(tag: String, msg: String) {
    if (isLoggable(tag, WARN)) {
        printLog(WARN, tag, msg)
    }
}

actual fun Log.e(tag: String, msg: String, throwable: Throwable?) {
    if (isLoggable(tag, ERROR)) {
        printLog(ERROR, tag, msg, throwable)
    }
}

actual fun Log.wtf(tag: String, msg: String) {
    if (isLoggable(tag, ASSERT)) {
        printLog(ASSERT, tag, msg)
    }
}

actual fun Log.println(priority: Int, tag: String, msg: String) {
    if (isLoggable(tag, priority)) {
        printLog(priority, tag, msg)
    }
}
@file:OptIn(ExperimentalJsExport::class)

package com.kdroid.kmplog

import com.kdroid.kmplog.core.*

private fun getHtmlColor(priority: Int): String {
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

private fun printJsLog(priority: Int, tag: String, msg: String, throwable: Throwable? = null) {
    val style = getHtmlColor(priority)
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

@JsExport
actual fun Log.v(tag: String, msg: String) {
    if (isLoggable(tag, VERBOSE)) {
        printJsLog(VERBOSE, tag, msg)
    }
}
@JsExport
actual fun Log.d(tag: String, msg: String) {
    if (isLoggable(tag, DEBUG)) {
        printJsLog(DEBUG, tag, msg)
    }
}
@JsExport
actual fun Log.i(tag: String, msg: String) {
    if (isLoggable(tag, INFO)) {
        printJsLog(INFO, tag, msg)
    }
}
@JsExport
actual fun Log.w(tag: String, msg: String) {
    if (isLoggable(tag, WARN)) {
        printJsLog(WARN, tag, msg)
    }
}
@JsExport
actual fun Log.e(tag: String, msg: String, throwable: Throwable?) {
    if (isLoggable(tag, ERROR)) {
        printJsLog(ERROR, tag, msg, throwable)
    }
}
@JsExport
actual fun Log.wtf(tag: String, msg: String) {
    if (isLoggable(tag, ASSERT)) {
        printJsLog(ASSERT, tag, msg)
    }
}
@JsExport
actual fun Log.println(priority: Int, tag: String, msg: String) {
    if (isLoggable(tag, priority)) {
        printJsLog(priority, tag, msg)
    }
}


//Not Available on JS
actual fun printAndSendLog(priority: Int, tag: String, msg: String) {}
actual fun startServer() {}

//TODO
actual suspend fun sendMessageToWebSocket(logMessage: LogMessage) {}
package com.kdroid.kmplog

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

private const val RESET = "\u001B[0m"
private const val GRAY = "\u001B[90m"
private const val BLUE = "\u001B[34m"
private const val GREEN = "\u001B[32m"
private const val YELLOW = "\u001B[33m"
private const val RED = "\u001B[31m"
private const val MAGENTA = "\u001B[35m"

private const val HTML_GRAY = "#808080"
private const val HTML_BLUE = "#0000FF"
private const val HTML_GREEN = "#008000"
private const val HTML_YELLOW = "#FFFF00"
private const val HTML_RED = "#FF0000"
private const val HTML_MAGENTA = "#FF00FF"

private fun getColor(priority: Int): String {
    return when (priority) {
        Log.VERBOSE -> GRAY
        Log.DEBUG -> BLUE
        Log.INFO -> GREEN
        Log.WARN -> YELLOW
        Log.ERROR -> RED
        Log.ASSERT -> MAGENTA
        else -> RESET
    }
}

private fun getHtmlColor(priority: Int): String {
    return when (priority) {
        Log.VERBOSE -> HTML_GRAY
        Log.DEBUG -> HTML_BLUE
        Log.INFO -> HTML_GREEN
        Log.WARN -> HTML_YELLOW
        Log.ERROR -> HTML_RED
        Log.ASSERT -> HTML_MAGENTA
        else -> "#000000" // Noir par défaut
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

private fun printLog(priority: Int, tag: String, msg: String) {
    val timestamp = getCurrentDateTime()
    val priorityChar = getPriorityChar(priority)
    val formattedTag = formatTag(tag)
    val formattedMsg = formatMessage(msg)
    val logMessage = "$timestamp  $formattedTag  $priorityChar  $formattedMsg"
    val color = getColor(priority)
    println("$color$logMessage$RESET")

    val htmlColor = getHtmlColor(priority)
    val htmlLogMessage = """<span style="color:$htmlColor">$logMessage</span>"""

    CoroutineScope(Dispatchers.IO).launch { sendMessageToWebSocket(htmlLogMessage) }
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
        val logMessage = if (throwable != null) {
            "$msg\n${throwable.stackTraceToString()}"
        } else {
            msg
        }
        printLog(ERROR, tag, logMessage)
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


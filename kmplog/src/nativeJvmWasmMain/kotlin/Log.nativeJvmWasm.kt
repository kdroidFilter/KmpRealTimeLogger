package com.kdroid.kmplog

private const val RESET = "\u001B[0m"
private const val GRAY = "\u001B[90m"
private const val BLUE = "\u001B[34m"
private const val GREEN = "\u001B[32m"
private const val YELLOW = "\u001B[33m"
private const val RED = "\u001B[31m"
private const val MAGENTA = "\u001B[35m"


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

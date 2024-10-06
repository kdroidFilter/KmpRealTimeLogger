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

private fun printLog(priority: Int, tag: String, msg: String) {
    val timestamp = getCurrentDateTime()
    val priorityChar = getPriorityChar(priority)
    val logMessage = "$timestamp  $tag  $priorityChar  $msg"
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

actual fun Log.e(tag: String, msg: String) {
    if (isLoggable(tag, ERROR)) {
        printLog(ERROR, tag, msg)
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

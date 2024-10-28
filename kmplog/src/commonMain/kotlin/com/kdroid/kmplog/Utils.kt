package com.kdroid.kmplog

import com.kdroid.kmplog.Log.ASSERT
import com.kdroid.kmplog.Log.DEBUG
import com.kdroid.kmplog.Log.ERROR
import com.kdroid.kmplog.Log.INFO
import com.kdroid.kmplog.Log.VERBOSE
import com.kdroid.kmplog.Log.WARN
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

const val MAX_TAG_LENGTH = 20
const val MAX_MSG_LENGTH = 100

internal fun getCurrentDateTime(): String {
    val now = Clock.System.now()
    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

    return buildString {
        append(localDateTime.year.toString().padStart(4, '0'))
        append('-')
        append(localDateTime.monthNumber.toString().padStart(2, '0'))
        append('-')
        append(localDateTime.dayOfMonth.toString().padStart(2, '0'))
        append(' ')
        append(localDateTime.hour.toString().padStart(2, '0'))
        append(':')
        append(localDateTime.minute.toString().padStart(2, '0'))
        append(':')
        append(localDateTime.second.toString().padStart(2, '0'))
        append('.')
        append(localDateTime.nanosecond.toString().padStart(9, '0').substring(0, 3))
    }
}

internal fun getPriorityChar(priority: Int): String {
    return when (priority) {
        VERBOSE -> "[V]"
        DEBUG -> "[D]"
        INFO -> "[I]"
        WARN -> "[W]"
        ERROR -> "[E]"
        ASSERT -> "[A]"
        else -> "[U]"
    }
}

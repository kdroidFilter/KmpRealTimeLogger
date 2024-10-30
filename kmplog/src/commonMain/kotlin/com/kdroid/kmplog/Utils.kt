package com.kdroid.kmplog

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


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

expect fun getLocalIpAddress(): String?

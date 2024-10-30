package com.kdroid.kmplog.core

fun getPriorityChar(priority: Int): String {
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

fun getColor(priority: Int): String {
    return when (priority) {
        VERBOSE -> GRAY
        DEBUG -> BLUE
        INFO -> GREEN
        WARN -> YELLOW
        ERROR -> RED
        ASSERT -> MAGENTA
        else -> RESET
    }
}


 fun formatTag(tag: String, maxLength: Int = MAX_TAG_LENGTH): String {
    return if (tag.length > maxLength) {
        tag.substring(0, maxLength)
    } else {
        tag.padEnd(maxLength)
    }
}

 fun formatMessage(msg: String, maxLength: Int = MAX_MSG_LENGTH): String {
    return if (msg.length > maxLength) {
        msg.substring(0, maxLength - 3) + "..."
    } else {
        msg
    }
}


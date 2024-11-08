package com.kdroid.kmplog


actual fun printAndSendLog(priority: Int, tag: String, msg: String) {
    printAndGetLocalLog(tag = tag, message = msg, priority = priority)
}


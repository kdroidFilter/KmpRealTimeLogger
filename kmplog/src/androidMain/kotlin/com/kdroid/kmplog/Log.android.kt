package com.kdroid.kmplog

actual fun Log.v(tag: String, msg: String) {
    if (isLoggable(tag, VERBOSE)) android.util.Log.v(tag, msg)
}

actual fun Log.d(tag: String, msg: String) {
    if (isLoggable(tag, DEBUG))  android.util.Log.d(tag, msg)
}

actual fun Log.i(tag: String, msg: String) {
    if (isLoggable(tag, INFO))  android.util.Log.i(tag, msg)
}

actual fun Log.w(tag: String, msg: String) {
    if (isLoggable(tag, WARN)) android.util.Log.w(tag, msg)
}

actual fun Log.e(tag: String, msg: String) {
    if (isLoggable(tag, ERROR)) android.util.Log.e(tag, msg)

}

actual fun Log.wtf(tag: String, msg: String) {
    if (isLoggable(tag, ASSERT))  android.util.Log.wtf(tag, msg)
}

actual fun Log.println(priority: Int, tag: String, msg: String) {
    if (isLoggable(tag, priority)) android.util.Log.println(priority, tag, msg)
}

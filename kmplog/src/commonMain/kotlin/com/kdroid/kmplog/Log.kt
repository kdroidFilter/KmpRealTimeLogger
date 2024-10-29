package com.kdroid.kmplog

@Suppress("OPT_IN_USAGE")
object Log {
    init {
      startServer()
    }

    const val VERBOSE = 2
    const val DEBUG = 3
    const val INFO = 4
    const val WARN = 5
    const val ERROR = 6
    const val ASSERT = 7

    private var logLevel = DEBUG
    private var isDevelopmentMode = false

    fun isLoggable(tag: String, level: Int): Boolean {
        return isDevelopmentMode && level >= logLevel
    }

    fun setLogLevel(level: Int) {
        logLevel = level
    }

    fun setDevelopmentMode(isDevelopment: Boolean) {
        isDevelopmentMode = isDevelopment
    }
}

expect fun Log.v(tag: String, msg: String)
expect fun Log.d(tag: String, msg: String)
expect fun Log.i(tag: String, msg: String)
expect fun Log.w(tag: String, msg: String)
expect fun Log.e(tag: String, msg: String, throwable: Throwable? = null)
expect fun Log.wtf(tag: String, msg: String)
expect fun Log.println(priority: Int, tag: String, msg: String)


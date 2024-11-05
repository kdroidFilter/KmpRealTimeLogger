package com.kdroid.kmplog

class CrashHandler(private val logFunction: (String) -> Unit) : Thread.UncaughtExceptionHandler {
    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        // Log le crash dans ta bibliothèque
        logFunction(" ${throwable.localizedMessage}")

        // Appel de l'ancien gestionnaire si nécessaire
        defaultHandler?.uncaughtException(thread, throwable)
    }
}

actual fun initializeCrashHandler() {
    Thread.setDefaultUncaughtExceptionHandler(CrashHandler { message ->
            Log.e("CRASH CAPTURED", message)
    })
}
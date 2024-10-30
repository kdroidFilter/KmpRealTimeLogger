package com.kdroid.kmplog.client

import android.app.Application
import com.kdroid.kmplog.client.framework.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
        super.onCreate()
    }

}
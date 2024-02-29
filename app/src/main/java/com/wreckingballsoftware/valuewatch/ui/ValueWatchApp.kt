package com.wreckingballsoftware.valuewatch.ui

import android.app.Application
import com.wreckingballsoftware.valuewatch.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ValueWatchApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.INFO)
            androidContext(androidContext = this@ValueWatchApp)
            modules(modules = appModule)
        }
    }
}

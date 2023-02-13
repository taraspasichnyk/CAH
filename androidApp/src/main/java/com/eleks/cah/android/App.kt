package com.eleks.cah.android

import android.app.Application
import com.eleks.cah.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                allModules(this@App)
            )
        }
    }
}
package com.example.visualtransformationtextinput

import android.app.Application
import com.example.visualtransformationtextinput.di.appModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}
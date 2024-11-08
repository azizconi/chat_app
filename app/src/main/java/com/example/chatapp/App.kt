package com.example.chatapp

import android.app.Application
import com.example.chatapp.di.viewModelsModule
import com.example.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        AppModule.initKoin {
            androidContext(this@App)
            modules(viewModelsModule)
            androidLogger()
        }


    }

}
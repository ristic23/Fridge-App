package com.fridge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FridgeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
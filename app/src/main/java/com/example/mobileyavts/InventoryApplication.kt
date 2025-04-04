package com.example.mobileyavts

import android.app.Application
import com.example.mobileyavts.data.AppContainer
import com.example.mobileyavts.data.AppDataContainer

class InventoryApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}

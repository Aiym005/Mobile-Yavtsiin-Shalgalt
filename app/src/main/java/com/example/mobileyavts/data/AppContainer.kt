package com.example.mobileyavts.data

import android.content.Context

interface AppContainer {
    val itemsRepository: WordsRepository
    val settingsPreferencesRepository: SettingsPreferencesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val itemsRepository: WordsRepository by lazy {
        OfflineWordsRepository(WordDatabase.getDatabase(context).wordDao())
    }
    override val settingsPreferencesRepository: SettingsPreferencesRepository by lazy {
        SettingsPreferencesRepository(context.dataStore)
    }
}


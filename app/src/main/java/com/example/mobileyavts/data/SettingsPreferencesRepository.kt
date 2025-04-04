package com.example.mobileyavts.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsPreferencesRepository(private val dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences>) {
    private companion object {
        val SETTINGS_OPTION = intPreferencesKey("settingsOption")
        const val TAG = "settingsPreferencesRepository"
    }
    suspend fun saveSetting(option: Int) {
        dataStore.edit { preferences ->
            preferences[SETTINGS_OPTION] = option
        }
    }
    fun getSavedSetting(): Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[SETTINGS_OPTION] ?: 1
        }
}
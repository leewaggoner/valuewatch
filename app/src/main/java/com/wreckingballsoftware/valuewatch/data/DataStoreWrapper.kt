package com.wreckingballsoftware.valuewatch.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class DataStoreWrapper(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKey {
        val HOURLY_RATE = stringPreferencesKey("HourlyRate")
        val CURRENCY = stringPreferencesKey("Currency")
        val BACKGROUND_COLOR = stringPreferencesKey("BackgroundColor")
    }

    suspend fun putHourlyRate(rate: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.HOURLY_RATE] = rate
        }
    }

    suspend fun getHourlyRate(default: String): String = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.HOURLY_RATE] ?: default
    }

    suspend fun putCurrency(currency: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.CURRENCY] = currency
        }
    }

    suspend fun getCurrency(default: String): String = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.CURRENCY] ?: default
    }

    suspend fun setBackgroundColor(color: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.BACKGROUND_COLOR] = color
        }
    }

    suspend fun getBackgroundColor(default: String): String = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.BACKGROUND_COLOR] ?: default
    }

    suspend fun clearAll() = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

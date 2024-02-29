package com.wreckingballsoftware.valuewatch.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.wreckingballsoftware.valuewatch.data.CurrencyRepo
import com.wreckingballsoftware.valuewatch.data.DataStoreWrapper
import com.wreckingballsoftware.valuewatch.domain.Converter
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.PreferencesViewModel
import com.wreckingballsoftware.valuewatch.ui.watchscreen.WatchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DATA_STORE_NAME = "com.wreckingballsoftware.valuewatch"

val appModule = module {
    viewModel {
        PreferencesViewModel(
            handle = get(),
            currencyRepo = get(),
        )
    }

    viewModel {
        WatchViewModel(
            handle = get(),
        )
    }

    single {
        Converter(
            dataStoreWrapper = get(),
            currencyRepo = get(),
        )
    }

    single {
        CurrencyRepo(
            dataStoreWrapper = get(),
        )
    }

    single {
        DataStoreWrapper(getDataStore(androidContext()))
    }
}

private fun getDataStore(context: Context) : DataStore<Preferences> =
    PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
        produceFile = { context.preferencesDataStoreFile(DATA_STORE_NAME) },
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    )

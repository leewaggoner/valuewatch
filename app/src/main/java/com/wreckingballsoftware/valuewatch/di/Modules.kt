package com.wreckingballsoftware.valuewatch.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.wreckingballsoftware.valuewatch.data.BackgroundColorRepo
import com.wreckingballsoftware.valuewatch.data.CurrencyRepo
import com.wreckingballsoftware.valuewatch.data.DataStoreWrapper
import com.wreckingballsoftware.valuewatch.data.Timer
import com.wreckingballsoftware.valuewatch.ui.MainActivityViewModel
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
        MainActivityViewModel(
            handle = get(),
            currencyRepo = get(),
            backgroundColorRepo = get(),
        )
    }
    viewModel {
        PreferencesViewModel(
            handle = get(),
            backgroundColorRepo = get(),
            currencyRepo = get(),
        )
    }

    viewModel {
        WatchViewModel(
            handle = get(),
            currencyRepo = get(),
            timer = get(),
        )
    }

    single {
        CurrencyRepo(
            dataStoreWrapper = get(),
        )
    }

    single {
        BackgroundColorRepo(
            dataStoreWrapper = get(),
        )
    }

    single {
        DataStoreWrapper(getDataStore(androidContext()))
    }

    single {
        Timer(
            currencyRepo = get(),
        )
    }
}

private fun getDataStore(context: Context) : DataStore<Preferences> =
    PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
        produceFile = { context.preferencesDataStoreFile(DATA_STORE_NAME) },
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    )

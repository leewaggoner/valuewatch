package com.wreckingballsoftware.valuewatch.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.wreckingballsoftware.valuewatch.data.BackgroundColorRepo
import com.wreckingballsoftware.valuewatch.data.CurrencyRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    handle: SavedStateHandle,
    currencyRepo: CurrencyRepo,
    backgroundColorRepo: BackgroundColorRepo,
) : ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    var state by handle.saveable {
        mutableStateOf(MainActivityState())
    }

    init {
        viewModelScope.launch(Dispatchers.Main) {
//            currencyRepo.clear()
            currencyRepo.initialize()
            val skipPreferences = currencyRepo.getCurrentHourlyRate().isNotEmpty()
            state = state.copy(skipPreferences = skipPreferences)
            backgroundColorRepo.initialize()
            backgroundColorRepo.bgColor.collect { backgroundColor ->
                state = state.copy(backgroundColor = backgroundColor)
            }
        }
    }
}
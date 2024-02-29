package com.wreckingballsoftware.valuewatch.ui.preferencesscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.wreckingballsoftware.valuewatch.data.CurrencyRepo
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesEvent
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesNavigation
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class PreferencesViewModel(
    handle: SavedStateHandle,
    currencyRepo: CurrencyRepo,
): ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    var state by handle.saveable {
        mutableStateOf(PreferencesState())
    }
    val navigation = MutableSharedFlow<PreferencesNavigation>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    init{
        handleEvent(PreferencesEvent.NewCurrencyList(currencyRepo.currencyStrings()))
    }

    fun onHourlyRateChanged(hourlyRate: String) {
        state = state.copy(hourlyRate = hourlyRate)
    }

    fun handleEvent(event: PreferencesEvent) {
        state = when (event) {
            is PreferencesEvent.NewCurrencyList -> {
                state.copy(currencies = event.currencies)
            }
            is PreferencesEvent.ExpandedChanged -> {
                state.copy(dropdownExpanded = event.expanded)
            }
            PreferencesEvent.DismissDropdown -> {
                state.copy(dropdownExpanded = false)
            }
            is PreferencesEvent.CurrencySelected -> {
                state.copy(selectedCurrency = event.currency, dropdownExpanded = false)
            }
            is PreferencesEvent.HourlyRateChanged -> {
                state.copy(hourlyRate = event.hourlyRate)
            }
            PreferencesEvent.OnDoneClicked -> {
                viewModelScope.launch(Dispatchers.Main) {
                    navigation.emit(PreferencesNavigation.GoToWatchScreen)
                }
                state
            }
        }
    }
}

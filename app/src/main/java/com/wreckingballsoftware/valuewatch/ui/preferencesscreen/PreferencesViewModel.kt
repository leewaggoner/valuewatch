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
    private val currencyRepo: CurrencyRepo,
): ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    var state by handle.saveable {
        mutableStateOf(PreferencesState())
    }
    val navigation = MutableSharedFlow<PreferencesNavigation>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    init {
        viewModelScope.launch(Dispatchers.Main) {
            handleEvent(PreferencesEvent.NewCurrencyList(currencyRepo.currencies))
            handleEvent(PreferencesEvent.CurrencyChanged(currencyRepo.getCurrentCurrencyAbbreviation()))
            handleEvent(PreferencesEvent.HourlyRateChanged(currencyRepo.getCurrentHourlyRate()))
        }
    }

    fun onHourlyRateChanged(hourlyRate: String) {
        state = state.copy(hourlyRate = hourlyRate)
    }

    fun handleEvent(event: PreferencesEvent) {
        when (event) {
            is PreferencesEvent.NewCurrencyList -> {
                state = state.copy(currencies = event.currencies)
            }
            is PreferencesEvent.ExpandedChanged -> {
                state = state.copy(dropdownExpanded = event.expanded)
            }
            PreferencesEvent.DismissDropdown -> {
                state = state.copy(dropdownExpanded = false)
            }
            is PreferencesEvent.HourlyRateChanged -> {
                state = state.copy(hourlyRate = event.hourlyRate)
            }
            is PreferencesEvent.CurrencyChanged -> {
                viewModelScope.launch(Dispatchers.Main) {
                    currencyRepo.setCurrentCurrency(event.currency)
                    state = state.copy(
                        decimalSymbol = currencyRepo.decimalSymbol(),
                        selectedCurrency = currencyRepo.currencyName(),
                        currencySymbol = currencyRepo.currencySymbol(),
                        decimalDigits = currencyRepo.decimalDigits(),
                        thousandsSymbol = currencyRepo.thousandsSymbol(),
                        dropdownExpanded = false,
                    )
                }
            }
            PreferencesEvent.OnDoneClicked -> {
                viewModelScope.launch(Dispatchers.Main) {
                    currencyRepo.setCurrentHourlyRate(state.hourlyRate)
//                    currencyRepo.clear()
                    navigation.emit(PreferencesNavigation.GoToWatchScreen)
                }
            }
        }
    }
}

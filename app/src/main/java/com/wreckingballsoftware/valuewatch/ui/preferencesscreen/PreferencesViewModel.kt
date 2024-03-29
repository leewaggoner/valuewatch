package com.wreckingballsoftware.valuewatch.ui.preferencesscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.wreckingballsoftware.valuewatch.data.BackgroundColorRepo
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
    private val backgroundColorRepo: BackgroundColorRepo,
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
        handleEvent(PreferencesEvent.LoadCurrencyList(currencyRepo.currencies))
        handleEvent(PreferencesEvent.CurrencyChanged(currencyRepo.getCurrentCurrencyAbbreviation()))
        handleEvent(PreferencesEvent.HourlyRateChanged(currencyRepo.getCurrentHourlyRate()))
        handleEvent(PreferencesEvent.LoadBackgroundColors(backgroundColorRepo.backgroundColors))
        handleEvent(PreferencesEvent.InitSelectedBackgroundColor(backgroundColorRepo.getCurrentBackgroundColorIndex()))
    }

    fun handleEvent(event: PreferencesEvent) {
        when (event) {
            is PreferencesEvent.LoadCurrencyList -> {
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
            is PreferencesEvent.LoadBackgroundColors -> {
                state = state.copy(bgColors = event.bgColors)
            }
            is PreferencesEvent.InitSelectedBackgroundColor -> {
                state = state.copy(selectedBgColor = event.colorIndex)
            }
            is PreferencesEvent.BackgroundColorChanged -> {
                viewModelScope.launch(Dispatchers.Main) {
                    state = state.copy(selectedBgColor = event.colorIndex)
                    backgroundColorRepo.setBackgroundColor(state.bgColors[state.selectedBgColor].colorText)
                }
            }
            PreferencesEvent.OnBackClicked -> {
                viewModelScope.launch(Dispatchers.Main) {
                    currencyRepo.setCurrentHourlyRate(state.hourlyRate)
                    navigation.emit(PreferencesNavigation.GoBack)
                }
            }
            PreferencesEvent.OnDoneClicked -> {
                viewModelScope.launch(Dispatchers.Main) {
                    currencyRepo.setCurrentHourlyRate(state.hourlyRate)
                    navigation.emit(PreferencesNavigation.GoToWatchScreen)
                }
            }
        }
    }
}

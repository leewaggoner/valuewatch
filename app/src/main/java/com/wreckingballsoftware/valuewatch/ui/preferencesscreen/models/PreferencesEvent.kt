package com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models

import com.wreckingballsoftware.valuewatch.data.models.Currency

sealed interface PreferencesEvent {
    data class NewCurrencyList(val currencies: List<Currency>) : PreferencesEvent
    data class ExpandedChanged(val expanded: Boolean) : PreferencesEvent
    data object DismissDropdown : PreferencesEvent
    data class HourlyRateChanged(val hourlyRate: String) : PreferencesEvent
    data class CurrencyChanged(val currency: String) : PreferencesEvent
    data object OnDoneClicked : PreferencesEvent
}

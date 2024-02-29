package com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models

sealed interface PreferencesEvent {
    data class NewCurrencyList(val currencies: List<String>) : PreferencesEvent
    data class ExpandedChanged(val expanded: Boolean) : PreferencesEvent
    data object DismissDropdown : PreferencesEvent
    data class CurrencySelected(val currency: String) : PreferencesEvent
    data class HourlyRateChanged(val hourlyRate: String) : PreferencesEvent
    data object OnDoneClicked : PreferencesEvent
}

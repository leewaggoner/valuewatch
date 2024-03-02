package com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models

import com.wreckingballsoftware.valuewatch.data.models.BackgroundColor
import com.wreckingballsoftware.valuewatch.data.models.Currency

sealed interface PreferencesEvent {
    data class LoadCurrencyList(val currencies: List<Currency>) : PreferencesEvent
    data class ExpandedChanged(val expanded: Boolean) : PreferencesEvent
    data object DismissDropdown : PreferencesEvent
    data class HourlyRateChanged(val hourlyRate: String) : PreferencesEvent
    data class CurrencyChanged(val currency: String) : PreferencesEvent
    data object OnBackClicked : PreferencesEvent
    data object OnDoneClicked : PreferencesEvent
    data class LoadBackgroundColors(val bgColors: List<BackgroundColor>) : PreferencesEvent
    data class BackgroundColorChanged(val colorIndex: Int) : PreferencesEvent
}

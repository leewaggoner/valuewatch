package com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models

data class PreferencesState(
    val currencies: List<String> = listOf(),
    val selectedCurrency: String = "",
    val hourlyRate: String = "0.00",
    val dropdownExpanded: Boolean = false,
)

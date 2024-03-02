package com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models

import android.os.Parcelable
import com.wreckingballsoftware.valuewatch.data.models.BackgroundColor
import com.wreckingballsoftware.valuewatch.data.models.Currency
import kotlinx.parcelize.Parcelize

@Parcelize
data class PreferencesState(
    val currencies: List<Currency> = listOf(),
    val selectedCurrency: String = "",
    val hourlyRate: String = "",
    val currencySymbol: String = "",
    val decimalDigits: Int = 2,
    val thousandsSymbol: String = "",
    val decimalSymbol: String = "",
    val dropdownExpanded: Boolean = false,
    val bgColors: List<BackgroundColor> = listOf(),
    val selectedBgColor: Int = 0,
) : Parcelable


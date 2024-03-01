package com.wreckingballsoftware.valuewatch.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    val currency: String,
    val abbreviation: String,
    val symbol: String,
    val decimalDigits: Int = 2,
    val thousandsSymbol: String = ",",
    val decimalSymbol: String = ".",
) : Parcelable

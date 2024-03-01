package com.wreckingballsoftware.valuewatch.ui.watchscreen.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatchState(
    val time: String = "00:00:00",
    val currencySymbol: String = "",
    val money: String = "0.00",
    val isTiming: Boolean = false,
) : Parcelable

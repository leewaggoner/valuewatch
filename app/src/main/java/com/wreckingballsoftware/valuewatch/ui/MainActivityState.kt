package com.wreckingballsoftware.valuewatch.ui

import android.os.Parcelable
import com.wreckingballsoftware.valuewatch.data.models.BackgroundColor
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainActivityState(
    val skipPreferences: Boolean = true,
    val backgroundColor: BackgroundColor = BackgroundColor()
) : Parcelable


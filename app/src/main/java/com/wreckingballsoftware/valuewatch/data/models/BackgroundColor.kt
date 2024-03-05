package com.wreckingballsoftware.valuewatch.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BackgroundColor(
    val colorText: String = "White",
    val backgroundColor: Long = 0xFFFFFFFF,
    val textColor: Long = 0xFF000000,
) : Parcelable
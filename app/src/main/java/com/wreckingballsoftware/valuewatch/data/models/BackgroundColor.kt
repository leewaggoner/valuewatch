package com.wreckingballsoftware.valuewatch.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BackgroundColor(
    val colorText: String = "White",
    val backgroundColor: Int = 0xFFFFFFFF.toInt(),
    val textColor: Int = 0xFF000000.toInt(),
) : Parcelable
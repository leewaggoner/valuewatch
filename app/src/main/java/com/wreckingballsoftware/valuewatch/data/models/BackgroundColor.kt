package com.wreckingballsoftware.valuewatch.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BackgroundColor(
    val colorText: String,
    val backgroundColor: Int,
    val textColor: Int,
) : Parcelable
package com.wreckingballsoftware.valuewatch.data.models

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize

@Parcelize
data class BackgroundColor(
    val colorText: String = "White",
    val backgroundColor: Long = 0xFFFFFFFF,
    val textColor: Long = 0xFF000000,
) : Parcelable {
    fun getBackgroundColor(): Color {
        return Color(backgroundColor)
    }

    fun getTextColor(): Color {
        return Color(textColor)
    }
}
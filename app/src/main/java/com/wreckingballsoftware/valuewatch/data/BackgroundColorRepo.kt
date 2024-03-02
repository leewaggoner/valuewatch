package com.wreckingballsoftware.valuewatch.data

import androidx.compose.ui.graphics.Color
import com.wreckingballsoftware.valuewatch.data.models.BackgroundColor

class BackgroundColorRepo(
    private val dataStoreWrapper: DataStoreWrapper
) {
    private var currentBackgroundColor: BackgroundColor? = null
        get() = field ?: backgroundColors.first()

    val backgroundColors = listOf(
        BackgroundColor("White", 0xFFFFFFFF.toInt(), 0xFF000000.toInt()),
        BackgroundColor("Black", 0xFF000000.toInt(), 0xFFFFFFFF.toInt()),
        BackgroundColor("Red", 0xFFFF0000.toInt(), 0xFFFFFFFF.toInt()),
        BackgroundColor("Green", 0xFF00FF00.toInt(), 0xFFFFFFFF.toInt()),
        BackgroundColor("Blue", 0xFF0000FF.toInt(), 0xFFFFFFFF.toInt()),
        BackgroundColor("Yellow", 0xFFFFFF00.toInt(), 0xFF000000.toInt()),
        BackgroundColor("Magenta", 0xFFFF00FF.toInt(), 0xFFFFFFFF.toInt()),
        BackgroundColor("Cyan", 0xFF00FFFF.toInt(), 0xFF000000.toInt()),
        BackgroundColor("Gray", 0xFF888888.toInt(), 0xFF000000.toInt()),
    )

    suspend fun initialize() {
        val color = dataStoreWrapper.getBackgroundColor("")
        if (color.isNotEmpty()) {
            currentBackgroundColor = backgroundColors.first { it.colorText == color }
        }
    }

    suspend fun setBackgroundColor(color: String) {
        dataStoreWrapper.setBackgroundColor(color)
        currentBackgroundColor = backgroundColors.first { it.colorText == color }
    }

    fun getBackgroundColor(): Color {
        return Color(currentBackgroundColor?.backgroundColor ?: 0xFFFFFFFF.toInt())
    }

    fun getCurrentBackgroundColorIndex(): Int {
        return backgroundColors.indexOf(currentBackgroundColor)
    }
}

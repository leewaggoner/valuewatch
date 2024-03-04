package com.wreckingballsoftware.valuewatch.data

import androidx.compose.ui.graphics.Color
import com.wreckingballsoftware.valuewatch.data.models.BackgroundColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BackgroundColorRepo(
    private val dataStoreWrapper: DataStoreWrapper
) {
    private var currentBackgroundColor: BackgroundColor? = null
        get() = field ?: backgroundColors.first()

    val backgroundColors = listOf(
        BackgroundColor("White", 0xFFFFFFFF.toInt(), 0xFF000000.toInt()),
        BackgroundColor("Black", 0xFF000000.toInt(), 0xFFFFFFFF.toInt()),
        BackgroundColor("Pink", 0xFFFF80ED.toInt(), 0xFF000000.toInt()),
        BackgroundColor("Forest", 0xFF065535.toInt(), 0xFFFFFFFF.toInt()),
        BackgroundColor("Sand", 0xFFFFA500.toInt(), 0xFF000000.toInt()),
        BackgroundColor("Teal", 0xFF40E0D0.toInt(), 0xFFFFFFFF.toInt()),
        BackgroundColor("Peach", 0xFFFA8072.toInt(), 0xFFFFFFFF.toInt()),
        BackgroundColor("Red", 0xFF800000.toInt(), 0xFFFFFFFF.toInt()),
        BackgroundColor("Violet", 0xFF8A2BE2.toInt(), 0xFFFFFFFF.toInt()),
    )
    private val _bgColor = MutableStateFlow(backgroundColors.first())
    val bgColor: StateFlow<BackgroundColor> = _bgColor

    suspend fun initialize() {
        val color = dataStoreWrapper.getBackgroundColor("")
        if (color.isNotEmpty()) {
            currentBackgroundColor = backgroundColors.first { it.colorText == color }
        }
        _bgColor.emit(currentBackgroundColor ?: backgroundColors.first())
    }

    suspend fun setBackgroundColor(color: String) {
        dataStoreWrapper.setBackgroundColor(color)
        currentBackgroundColor = backgroundColors.first { it.colorText == color }
        _bgColor.emit(currentBackgroundColor ?: backgroundColors.first())
    }

    fun getBackgroundColor(): Color {
        return Color(currentBackgroundColor?.backgroundColor ?: 0xFFFFFFFF.toInt())
    }

    fun getCurrentBackgroundColorIndex(): Int {
        return backgroundColors.indexOf(currentBackgroundColor)
    }
}

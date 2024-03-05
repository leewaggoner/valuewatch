package com.wreckingballsoftware.valuewatch.data

import com.wreckingballsoftware.valuewatch.data.models.BackgroundColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BackgroundColorRepo(
    private val dataStoreWrapper: DataStoreWrapper
) {
    private var currentBackgroundColor: BackgroundColor? = null
        get() = field ?: backgroundColors.first()

    val backgroundColors = listOf(
        BackgroundColor("White", 0xFFFFFFFF, 0xFF000000),
        BackgroundColor("Black", 0xFF000000, 0xFFFFFFFF),
        BackgroundColor("Pink", 0xFFFF80ED, 0xFF000000),
        BackgroundColor("Forest", 0xFF065535, 0xFFFFFFFF),
        BackgroundColor("Sand", 0xFFFFA500, 0xFF000000),
        BackgroundColor("Teal", 0xFF40E0D0, 0xFF000000),
        BackgroundColor("Peach", 0xFFFA8072, 0xFF000000),
        BackgroundColor("Red", 0xFF800000, 0xFFFFFFFF),
        BackgroundColor("Violet", 0xFF8A2BE2, 0xFFFFFFFF),
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

    fun getCurrentBackgroundColorIndex(): Int {
        return backgroundColors.indexOf(currentBackgroundColor)
    }
}

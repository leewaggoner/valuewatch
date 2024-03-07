package com.wreckingballsoftware.valuewatch.ui.timerscreen.models

sealed interface TimerNavigation {
    data object GoToPreferences : TimerNavigation
}
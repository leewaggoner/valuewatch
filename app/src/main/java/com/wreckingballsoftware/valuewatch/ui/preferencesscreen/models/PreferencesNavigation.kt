package com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models

sealed interface PreferencesNavigation {
    data object GoToWatchScreen : PreferencesNavigation
}

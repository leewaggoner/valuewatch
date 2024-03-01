package com.wreckingballsoftware.valuewatch.ui.watchscreen.models

sealed interface WatchNavigation {
    data object GoToPreferences : WatchNavigation
}
package com.wreckingballsoftware.valuewatch.ui.watchscreen.models

sealed interface WatchEvent {
    data object Initialize : WatchEvent
    data object OnPreferencesButtonClicked : WatchEvent
    data object OnPlayButtonClicked : WatchEvent
    data object OnResetButtonClicked : WatchEvent
    data class UpdateMoney(val money: String) : WatchEvent
    data class UpdateTime(val time: String) : WatchEvent
}
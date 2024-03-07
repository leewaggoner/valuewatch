package com.wreckingballsoftware.valuewatch.ui.timerscreen.models

sealed interface TimerEvent {
    data object Initialize : TimerEvent
    data object OnPreferencesButtonClicked : TimerEvent
    data object OnPlayButtonClicked : TimerEvent
    data object OnResetButtonClicked : TimerEvent
    data class UpdateMoney(val money: String) : TimerEvent
    data class UpdateTime(val time: String) : TimerEvent
}
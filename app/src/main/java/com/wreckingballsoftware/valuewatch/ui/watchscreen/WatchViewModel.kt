package com.wreckingballsoftware.valuewatch.ui.watchscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.wreckingballsoftware.valuewatch.data.CurrencyRepo
import com.wreckingballsoftware.valuewatch.data.Timer
import com.wreckingballsoftware.valuewatch.ui.watchscreen.models.WatchEvent
import com.wreckingballsoftware.valuewatch.ui.watchscreen.models.WatchNavigation
import com.wreckingballsoftware.valuewatch.ui.watchscreen.models.WatchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class WatchViewModel(
    handle: SavedStateHandle,
    currencyRepo: CurrencyRepo,
    private val timer: Timer,
): ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    var state by handle.saveable {
        mutableStateOf(WatchState(currencySymbol = currencyRepo.currencySymbol()))
    }
    val navigation = MutableSharedFlow<WatchNavigation>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    init {
        viewModelScope.launch(Dispatchers.Main) {
            timer.ticker.collect { money ->
                handleEvent(WatchEvent.UpdateMoney(money))
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            timer.time.collect { time ->
                state = state.copy(time = time)
            }
        }
    }

    private fun startTimer(startTimer: Boolean) {
        if (startTimer) {
            timer.startTimer(viewModelScope)
        } else {
            timer.pauseTimer()
        }
    }

    private fun resetTimer() {
        timer.stopTimer()
    }

    fun handleEvent(event: WatchEvent) {
        when (event) {
            WatchEvent.OnPlayButtonClicked -> {
                state = state.copy(isTiming = !state.isTiming)
                startTimer(state.isTiming)
            }
            WatchEvent.OnResetButtonClicked -> {
                state = state.copy(isTiming = false, time = "00:00:00", money = "0")
                resetTimer()
            }
            is WatchEvent.UpdateMoney -> {
                state = state.copy(money = event.money)
            }
            WatchEvent.OnPreferencesButtonClicked -> {
                navigation.tryEmit(WatchNavigation.GoToPreferences)
            }
        }
    }
}

package com.wreckingballsoftware.valuewatch.ui.timerscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.wreckingballsoftware.valuewatch.data.CurrencyRepo
import com.wreckingballsoftware.valuewatch.data.Timer
import com.wreckingballsoftware.valuewatch.ui.timerscreen.models.TimerEvent
import com.wreckingballsoftware.valuewatch.ui.timerscreen.models.TimerNavigation
import com.wreckingballsoftware.valuewatch.ui.timerscreen.models.TimerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class TimerViewModel(
    handle: SavedStateHandle,
    private val currencyRepo: CurrencyRepo,
    private val timer: Timer,
) : ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    var state by handle.saveable {
        mutableStateOf(TimerState())
    }
    val navigation = MutableSharedFlow<TimerNavigation>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    init {
        viewModelScope.launch(Dispatchers.Main) {
            timer.ticker.collect { money ->
                handleEvent(TimerEvent.UpdateMoney(money))
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            timer.time.collect { time ->
                handleEvent(TimerEvent.UpdateTime(time))
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

    fun handleEvent(event: TimerEvent) {
        when (event) {
            TimerEvent.Initialize -> {
                state = state.copy(
                    time = "00:00:00",
                    money = "0.00",
                    currencySymbol = currencyRepo.currencySymbol(),
                    isTiming = false,
                )
            }
            TimerEvent.OnPlayButtonClicked -> {
                val startTimer = !state.isTiming
                startTimer(startTimer)
                state = state.copy(isTiming = startTimer)
            }
            is TimerEvent.UpdateMoney -> {
                state = state.copy(money = event.money)
            }
            is TimerEvent.UpdateTime -> {
                state = state.copy(time = event.time)
            }
            TimerEvent.OnResetButtonClicked -> {
                timer.stopTimer()
                state = state.copy(isTiming = false)
            }
            TimerEvent.OnPreferencesButtonClicked -> {
                timer.stopTimer()
                viewModelScope.launch(Dispatchers.Main) {
                    navigation.emit(TimerNavigation.GoToPreferences)
                }
            }
        }
    }
}
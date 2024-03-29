package com.wreckingballsoftware.valuewatch.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.time.Duration.Companion.seconds

class Timer(
    private val currencyRepo: CurrencyRepo,
) {
    private var seconds: Long = 0
    private var job: Job? = null
    private val mutableTicker = MutableStateFlow("0")
    val ticker: StateFlow<String> = mutableTicker
    private val mutableTime = MutableStateFlow("00:00:00")
    val time: StateFlow<String> = mutableTime

    fun startTimer(coroutineScope: CoroutineScope) {
        clearValues()
        if (job == null) {
            startJob(coroutineScope)
        }
    }

    private fun startJob(coroutineScope: CoroutineScope) {
        job = coroutineScope.launch(Dispatchers.Main) {
            while (isActive) {
                mutableTicker.value = secondsToMoney(seconds)
                mutableTime.value = secondsToTime(seconds)
                seconds++
                delay(1.seconds)
            }
        }
    }

    fun pauseTimer() {
        stopJob()
    }

    fun stopTimer() {
        clearValues()
        stopJob()
        seconds = 0
    }

    private fun stopJob() {
        job?.cancel()
        job = null
    }

    private fun clearValues() {
        mutableTicker.value = "0"
        mutableTime.value = "00:00:00"
    }

    private fun secondsToMoney(seconds: Long): String {
        if (seconds <= 0) {
            return "0"
        }

        var rate = currencyRepo.getCurrentHourlyRate()
        if (rate.isEmpty()) {
            return "0"
        }

        val decimalDigits = currencyRepo.currencyDecimalDigits()
        if (decimalDigits > 0 && rate.length >= decimalDigits) {
            rate = StringBuilder(rate).insert(rate.length - decimalDigits, ".")
                .toString()
        }

        val hourlyRate = BigDecimal(rate)

        return (hourlyRate * BigDecimal(seconds) / BigDecimal(3600))
            .setScale(decimalDigits, RoundingMode.DOWN)
            .toString()
    }

    private fun secondsToTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
}
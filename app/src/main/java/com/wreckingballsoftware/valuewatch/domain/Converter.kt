package com.wreckingballsoftware.valuewatch.domain

import com.wreckingballsoftware.valuewatch.data.CurrencyRepo
import java.math.BigDecimal
import java.math.RoundingMode

class Converter(
    private val currencyRepo: CurrencyRepo,
) {
    suspend fun secondsToMoney(seconds: Long): String {
        if (seconds <= 0) {
            return ""
        }

        val hourlyRate = currencyRepo.getCurrentHourlyRate().toBigDecimal()
        return (hourlyRate * BigDecimal(seconds) / BigDecimal(3600))
            .setScale(currencyRepo.currencyDecimalDigits(), RoundingMode.HALF_UP)
            .toString()
    }
}

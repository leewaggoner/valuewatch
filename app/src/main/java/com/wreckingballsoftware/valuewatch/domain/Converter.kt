package com.wreckingballsoftware.valuewatch.domain

import com.wreckingballsoftware.valuewatch.data.CurrencyRepo
import com.wreckingballsoftware.valuewatch.data.DataStoreWrapper
import java.math.BigDecimal
import java.math.RoundingMode

class Converter(
    private val currencyRepo: CurrencyRepo,
    private val dataStoreWrapper: DataStoreWrapper,
) {
    suspend fun secondsToMoney(seconds: Long): String {
        if (seconds <= 0) {
            return ""
        }

        val hourlyRate = dataStoreWrapper.getHourlyRate("0.00").toBigDecimal()
        return (hourlyRate * BigDecimal(seconds) / BigDecimal(3600))
            .setScale(currencyRepo.currencyDecimalDigits(), RoundingMode.HALF_UP)
            .toString()
    }
}

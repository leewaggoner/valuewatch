package com.wreckingballsoftware.valuewatch.data

import com.wreckingballsoftware.valuewatch.data.models.Currency

class CurrencyRepo(
    private val dataStoreWrapper: DataStoreWrapper,
) {
    val currencies = listOf(
        Currency(
            currency = "British Pound",
            abbreviation = "GBP",
            symbol = "£",
        ),
        Currency(
            currency = "Canadian Dollar",
            abbreviation = "CAD",
            symbol = "$",
        ),
        Currency(
            currency = "Chinese Yuan",
            abbreviation = "CNY",
            symbol = "¥",
        ),
        Currency(
            currency = "Euro",
            abbreviation = "EUR",
            symbol = "€",
            thousandsSymbol = ".",
            decimalSymbol = ",",
        ),
        Currency(
            currency = "Hong Kong Dollar",
            abbreviation = "HKD",
            symbol = "$",
        ),
        Currency(
            currency = "Indian Rupee",
            abbreviation = "INR",
            symbol = "₹",
        ),
        Currency(
            currency = "Japanese Yen",
            abbreviation = "JPY",
            symbol = "¥",
            decimalDigits = 0,
            decimalSymbol = "",
        ),
        Currency(
            currency = "Mexican Peso",
            abbreviation = "MXN",
            symbol = "$",
        ),
        Currency(
            currency = "Russian Ruble",
            abbreviation = "RUB",
            symbol = "₽",
            thousandsSymbol = " ",
            decimalSymbol = ",",
        ),
        Currency(
            currency = "US Dollar",
            abbreviation = "USD",
            symbol = "$",
        ),
    )
    private var currentHourlyRate: String = ""
    private var currentCurrencyAbbreviation: String = ""

    suspend fun initialize() {
        currentHourlyRate = dataStoreWrapper.getHourlyRate("")
        currentCurrencyAbbreviation = dataStoreWrapper.getCurrency("USD")
    }

    fun getCurrentCurrencyAbbreviation(): String {
        return currentCurrencyAbbreviation
    }

    fun getCurrentHourlyRate(): String {
        return currentHourlyRate
    }

    suspend fun setCurrentCurrency(currency: String) {
        currentCurrencyAbbreviation = currency
        dataStoreWrapper.putCurrency(currency)
    }

    suspend fun setCurrentHourlyRate(rate: String) {
        currentHourlyRate = rate
        dataStoreWrapper.putHourlyRate(rate)
    }

    private val currencyMap: Map<String, Currency> = currencies.associateBy { currency ->
        currency.abbreviation
    }

    fun currencyDecimalDigits(): Int {
        return currencyMap[currentCurrencyAbbreviation]?.decimalDigits ?: 2
    }

    fun currencyName(): String {
        return currencyMap[currentCurrencyAbbreviation]?.currency ?: "US Dollar"
    }

    fun currencySymbol(): String {
        return currencyMap[currentCurrencyAbbreviation]?.symbol ?: "$"
    }

    fun decimalDigits(): Int {
        return currencyMap[currentCurrencyAbbreviation]?.decimalDigits ?: 2
    }

    fun thousandsSymbol(): String {
        return currencyMap[currentCurrencyAbbreviation]?.thousandsSymbol ?: ","
    }

    fun decimalSymbol(): String {
        return currencyMap[currentCurrencyAbbreviation]?.decimalSymbol ?: "."
    }

    suspend fun clear() {
        dataStoreWrapper.clearAll()
    }
}

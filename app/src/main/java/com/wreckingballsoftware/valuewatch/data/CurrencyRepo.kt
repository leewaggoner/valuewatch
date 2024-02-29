package com.wreckingballsoftware.valuewatch.data

import com.wreckingballsoftware.valuewatch.data.models.Currency

class CurrencyRepo(
    private val dataStoreWrapper: DataStoreWrapper,
) {
    private val currencies = listOf(
        Currency("Australian Dollar", "AUD", "$"),
        Currency("Brazilian Real", "BRL", "R$"),
        Currency("British Pound", "GBP", "£"),
        Currency("Canadian Dollar", "CAD", "$"),
        Currency("Chinese Yuan", "CNY", "¥"),
        Currency("Euro", "EUR", "€"),
        Currency("Hong Kong Dollar", "HKD", "$"),
        Currency("Indian Rupee", "INR", "₹"),
        Currency("Indonesian Rupiah", "IDR", "Rp", 0),
        Currency("Japanese Yen", "JPY", "¥", 0),
        Currency("Malaysian Ringgit", "MYR", "RM"),
        Currency("Mexican Peso", "MXN", "$"),
        Currency("Philippine Peso", "PHP", "₱"),
        Currency("Russian Ruble", "RUB", "₽"),
        Currency("Singapore Dollar", "SGD", "$"),
        Currency("South Korean Won", "KRW", "₩", 0),
        Currency("Thai Baht", "THB", "฿"),
        Currency("US Dollar", "USD", "$"),
    )
    private val currencyMap: Map<String, Currency> = currencies.associateBy { currency ->
        currency.abbreviation
    }

    suspend fun currencyDecimalDigits(): Int {
        val currentCurrency = dataStoreWrapper.getCurrency("USD")
        return currencyMap[currentCurrency]?.decimalDigits ?: 2
    }

    fun currencyStrings(): List<String> = currencies.map { currency ->
        currency.currency
    }
}

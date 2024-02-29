package com.wreckingballsoftware.valuewatch.data.models

data class Currency(
    val currency: String,
    val abbreviation: String,
    val symbol: String,
    val decimalDigits: Int = 2
)

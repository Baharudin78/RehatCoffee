package com.rehat.rehatcoffee.presentation.common.extention

import java.text.NumberFormat
import java.util.*

fun generateIDRCurrency(value: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    formatter.currency = Currency.getInstance("IDR")
    return formatter.format(value)
}
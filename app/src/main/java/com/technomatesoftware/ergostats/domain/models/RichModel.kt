package com.technomatesoftware.ergostats.domain.models

import java.text.DecimalFormat

data class RichModel(val address: String, val balance: Long) {
    fun getTrimmedAddress(): String {
        return address.substring(0, 6)
    }

    fun getFormattedBalance(): String {
        val ergBalance = balance.div(1000000000)
        val decimalFormat = DecimalFormat("#,###")
        val formattedNumber = decimalFormat.format(ergBalance)
        return "$formattedNumber ERG"
    }
}

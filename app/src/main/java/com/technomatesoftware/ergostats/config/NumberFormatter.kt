package com.technomatesoftware.ergostats.config

import java.math.RoundingMode

class NumberFormatter {
    fun toPercentWithDecimals(number: Double?, decimals: Int): String {
        if (number != null) {
            return "${
                number.toBigDecimal()
                    .setScale(decimals, RoundingMode.HALF_UP)
            }%"
        }
        return EMPTY_STRING
    }
}
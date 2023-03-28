package com.technomatesoftware.ergostats.config

import java.math.RoundingMode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NumberFormatter @Inject constructor() {
    fun toPercentWithDecimals(number: Double?, decimals: Int): String {
        if (number != null) {

//            val df = DecimalFormat("#,###.00")
//            df.roundingMode = RoundingMode.HALF_UP
//            return "${df.format(number)}%"
            return "${
                number.toBigDecimal()
                    .setScale(decimals, RoundingMode.HALF_UP)
            }%"
        }
        return EMPTY_STRING
    }
}
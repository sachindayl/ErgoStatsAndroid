package com.technomatesoftware.ergostats.domain.models

import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class AgeUSDModel(
    val reserveRatio: Int,
    @SerializedName("sigUsdPrice")
    val sigmaUSDPrice: Int,
    @SerializedName("sigRsvPrice")
    val sigmaReservePrice: Int,
) {
    fun getFormattedSigmaUSD(): String {
        val decimalFormat = DecimalFormat("#,###.######")
        val realValue = sigmaUSDPrice.div(10000000.0)
        return decimalFormat.format(realValue)
    }

    fun getFormattedSigmaUSDPerErg(): String {
        val decimalFormat = DecimalFormat("#,###.##")
        val realValue = sigmaUSDPrice.div(10000000.0)
        val valueToErg = 1.div(realValue)
        return decimalFormat.format(valueToErg)
    }

    fun getFormattedSigmaReserve(): String {
        val decimalFormat = DecimalFormat("#,###.#########")
        val realValue = sigmaReservePrice.div(1000000000.0)
        return decimalFormat.format(realValue)
    }

    fun getFormattedSigmaReservePerErg(): String {
        val decimalFormat = DecimalFormat("#,###")
        val realValue = sigmaReservePrice.div(1000000000.0)
        val valueToErg = 1.div(realValue)
        return decimalFormat.format(valueToErg)
    }
}

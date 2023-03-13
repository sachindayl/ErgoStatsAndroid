package com.technomatesoftware.ergostats.domain.models

import android.util.Log
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CoinMarketPriceChartDataModel(val prices: List<List<Double>>) {
    fun getDate(dateIndex: Int): String {
        Log.d("getDate", prices[dateIndex].first().toString())
        return SimpleDateFormat("MMM d", Locale.getDefault()).format(
            Date(prices[dateIndex].first().toLong())
        )
    }

    fun getPrice(dateIndex: Int): String {
        val priceOnDate = prices[dateIndex][1]
        val formatter = DecimalFormat("#,###.##")
        return "$${formatter.format(priceOnDate)}"
    }
}

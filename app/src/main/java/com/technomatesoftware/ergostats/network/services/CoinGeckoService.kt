package com.technomatesoftware.ergostats.network.services

import com.technomatesoftware.ergostats.domain.models.CoinMarketDataModel
import com.technomatesoftware.ergostats.domain.models.CoinMarketPriceChartDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoService {
    // coins/markets?vs_currency=usd&ids=ergo&order=market_cap_desc&per_page=10&page=1&sparkline=true&price_change_percentage=24h

    @GET("coins/markets")
    suspend fun getCoinMarketData(
        @Query("vs_currency") vsCurrency: String,
        @Query("ids") ids: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("sparkline") sparkLine: Boolean,
        @Query("price_change_percentage") priceChangePercentage: String,
    ) : List<CoinMarketDataModel>

    @GET("coins/ergo/market_chart")
    suspend fun getCoinMarketPriceChartData(
        @Query("vs_currency") vsCurrency: String,
        @Query("days") days: Int,
        @Query("interval") interval: String,
    ) : CoinMarketPriceChartDataModel
}
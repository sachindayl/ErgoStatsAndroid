package com.technomatesoftware.ergostats.network.interfaces

import com.technomatesoftware.ergostats.domain.models.CoinMarketDataModel
import com.technomatesoftware.ergostats.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface CoinGeckoRepository {
    suspend fun getCoinMarketData(): Flow<Response<List<CoinMarketDataModel>>>
    suspend fun getStoredCoinMarketData(): Flow<Response<List<CoinMarketDataModel>>>
    suspend fun replaceCoinMarketData(coinMarketDataList: List<CoinMarketDataModel>)
    suspend fun replaceMarketChartData(chartDataList: List<List<Double>>)
    suspend fun getStoredMarketChartData(): Flow<Response<List<List<Double>>>>
    suspend fun getCoinMarketPriceChartData(): Flow<Response<List<List<Double>>>>
}
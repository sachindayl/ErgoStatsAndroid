package com.technomatesoftware.ergostats.network.interfaces

import com.technomatesoftware.ergostats.domain.models.CoinMarketDataModel
import com.technomatesoftware.ergostats.domain.models.CoinMarketPriceChartDataModel
import com.technomatesoftware.ergostats.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface CoinGeckoRepository {
    suspend fun getCoinMarketData(): Flow<Response<List<CoinMarketDataModel>>>
    suspend fun getCoinMarketPriceChartData(): Flow<Response<CoinMarketPriceChartDataModel>>
}
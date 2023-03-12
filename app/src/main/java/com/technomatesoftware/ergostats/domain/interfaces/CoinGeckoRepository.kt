package com.technomatesoftware.ergostats.domain.interfaces

import com.technomatesoftware.ergostats.domain.models.CoinGeckoModel
import com.technomatesoftware.ergostats.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface CoinGeckoRepository {
    suspend fun getCoinMarketData(): Flow<Response<List<CoinGeckoModel>>>
}
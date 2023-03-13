package com.technomatesoftware.ergostats.network.repository

import android.util.Log
import com.technomatesoftware.ergostats.domain.interfaces.CoinGeckoRepository
import com.technomatesoftware.ergostats.domain.models.CoinMarketDataModel
import com.technomatesoftware.ergostats.domain.models.CoinMarketPriceChartDataModel
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.network.services.CoinGeckoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinGeckoRepositoryImpl @Inject constructor(
    private val coinGeckoService: CoinGeckoService
) : CoinGeckoRepository {
    override suspend fun getCoinMarketData(): Flow<Response<List<CoinMarketDataModel>>> = flow {
        try {
            emit(Response.Loading)
            val result = coinGeckoService.getCoinMarketData(
                vsCurrency = "usd",
                ids = "ergo",
                order = "market_cap_desc",
                perPage = 10,
                page = 1,
                sparkLine = true,
                priceChangePercentage = "30d"
            )
            emit(Response.Success(result))
        } catch (e: Exception) {
            Log.d("getCoinMarketData", e.message.toString())
            emit(Response.Failure(e))
        }

    }.flowOn(Dispatchers.IO)

    override suspend fun getCoinMarketPriceChartData(): Flow<Response<CoinMarketPriceChartDataModel>> =
        flow {
            try {
                emit(Response.Loading)
                val result = coinGeckoService.getCoinMarketPriceChartData(
                    vsCurrency = "usd",
                    days = 30,
                    interval = "daily"
                )
                emit(Response.Success(result))
            } catch (e: Exception) {
                Log.d("getCoinMarketData", e.message.toString())
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)


}
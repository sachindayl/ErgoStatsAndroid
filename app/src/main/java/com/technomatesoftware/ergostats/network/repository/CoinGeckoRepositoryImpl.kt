package com.technomatesoftware.ergostats.network.repository

import android.util.Log
import com.technomatesoftware.ergostats.domain.dao.CoinGeckoDao
import com.technomatesoftware.ergostats.domain.models.CoinMarketDataModel
import com.technomatesoftware.ergostats.domain.models.CoinMarketPriceChartDataModel
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.network.interfaces.CoinGeckoRepository
import com.technomatesoftware.ergostats.network.services.CoinGeckoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinGeckoRepositoryImpl @Inject constructor(
    private val coinGeckoService: CoinGeckoService,
    private val coinGeckoDao: CoinGeckoDao
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

    override suspend fun getStoredCoinMarketData(): Flow<Response<List<CoinMarketDataModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val coinMarketDataList = coinGeckoDao.getCoinMarketDataAndSparkline()
                if (coinMarketDataList.isNotEmpty()) {
                    val storedData = coinMarketDataList.first()
                    val mappedData = CoinMarketDataModel(
                        id = storedData.coinId,
                        symbol = storedData.symbol,
                        name = storedData.name,
                        image = storedData.image,
                        currentPrice = storedData.currentPrice,
                        marketCap = storedData.marketCap,
                        marketCapRank = storedData.marketCapRank,
                        fullyDilutedValuation = storedData.fullyDilutedValuation,
                        totalVolume = storedData.totalVolume,
                        high24H = storedData.high24H,
                        low24H = storedData.low24H,
                        priceChange24H = storedData.priceChange24H,
                        priceChangePercentage24H = storedData.priceChangePercentage24H,
                        marketCapChange24H = storedData.marketCapChange24H,
                        marketCapChangePercentage24H = storedData.marketCapChangePercentage24H,
                        circulatingSupply = storedData.circulatingSupply,
                        totalSupply = storedData.totalSupply,
                        maxSupply = storedData.maxSupply,
                        ath = storedData.ath,
                        athChangePercentage = storedData.athChangePercentage,
                        athDate = storedData.athDate,
                        atl = storedData.atl,
                        atlChangePercentage = storedData.atlChangePercentage,
                        atlDate = storedData.atlDate,
                        lastUpdated = storedData.lastUpdated,
                        roi = storedData.roi,
                        priceChangePercentage24HInCurrency = storedData.priceChangePercentage24HInCurrency
                    )
                    emit(Response.Success(listOf(mappedData)))
                } else {
                    emit(Response.Success(emptyList()))
                }
            } catch (e: Exception) {
                Log.d("getStoredCoinMarketData", e.message.toString())
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun storeCoinMarketData(coinMarketDataList: List<CoinMarketDataModel>) {
        val item = coinMarketDataList.first()
        coinGeckoDao.insertCoinMarketData(item.toCoinMarketDataEntity())
    }

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
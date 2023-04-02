package com.technomatesoftware.ergostats.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technomatesoftware.ergostats.domain.entities.CoinMarketDataEntity
import com.technomatesoftware.ergostats.domain.entities.MarketChartDataEntity

@Dao
interface CoinGeckoDao {

    @Query("SELECT * FROM coin_market_data")
    suspend fun getCoinMarketDataAndSparkline(): List<CoinMarketDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinMarketData(vararg coinMarketData: CoinMarketDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinMarketChartData(marketChartData: List<MarketChartDataEntity>)

    @Query("SELECT * FROM coin_market_chart_data")
    suspend fun getMarketChartData(): List<MarketChartDataEntity>


    @Query("DELETE FROM coin_market_data")
    suspend fun clearCoinMarketData()

    @Query("DELETE FROM coin_market_chart_data")
    suspend fun clearMarketChartData()


}
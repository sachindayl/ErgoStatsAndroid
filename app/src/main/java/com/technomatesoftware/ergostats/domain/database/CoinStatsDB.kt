package com.technomatesoftware.ergostats.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.technomatesoftware.ergostats.domain.dao.CoinGeckoDao
import com.technomatesoftware.ergostats.domain.dao.ErgoWatchDao
import com.technomatesoftware.ergostats.domain.entities.CoinMarketDataEntity
import com.technomatesoftware.ergostats.domain.entities.MarketChartDataEntity
import com.technomatesoftware.ergostats.domain.entities.SummaryMetricsEntity

@Database(
    entities = [
        CoinMarketDataEntity::class,
        MarketChartDataEntity::class,
        SummaryMetricsEntity::class],
    version = 2,
    exportSchema = true
)
abstract class CoinStatsDB : RoomDatabase() {
    abstract fun coinGeckoDao(): CoinGeckoDao
    abstract fun ergoWatchDao(): ErgoWatchDao
}
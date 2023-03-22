package com.technomatesoftware.ergostats.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.technomatesoftware.ergostats.domain.dao.CoinGeckoDao
import com.technomatesoftware.ergostats.domain.dao.ErgoPlatformDao
import com.technomatesoftware.ergostats.domain.dao.ErgoWatchDao
import com.technomatesoftware.ergostats.domain.entities.CoinMarketDataEntity
import com.technomatesoftware.ergostats.domain.entities.MarketChartDataEntity
import com.technomatesoftware.ergostats.domain.entities.SummaryMetricsEntity
import com.technomatesoftware.ergostats.domain.entities.TokenSupplyEntity

@Database(
    entities = [
        CoinMarketDataEntity::class,
        MarketChartDataEntity::class,
        SummaryMetricsEntity::class,
        TokenSupplyEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class ErgoStatsDB : RoomDatabase() {
    abstract fun coinGeckoDao(): CoinGeckoDao
    abstract fun ergoWatchDao(): ErgoWatchDao
    abstract fun ergoPlatformDao(): ErgoPlatformDao
}
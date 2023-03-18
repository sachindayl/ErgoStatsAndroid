package com.technomatesoftware.ergostats.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.technomatesoftware.ergostats.domain.dao.CoinGeckoDao
import com.technomatesoftware.ergostats.domain.entities.CoinMarketDataEntity
import com.technomatesoftware.ergostats.domain.entities.MarketChartDataEntity

@Database(entities = [CoinMarketDataEntity::class, MarketChartDataEntity::class], version = 1, exportSchema = true)
abstract class CoinStatsDB : RoomDatabase() {
    abstract fun coinGeckoDao(): CoinGeckoDao
}
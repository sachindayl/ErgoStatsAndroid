package com.technomatesoftware.ergostats.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_market_chart_data")
data class MarketChartDataEntity(
    @ColumnInfo(name = "market_chart_id") @PrimaryKey(autoGenerate = true) val marketChartId: Long = 0,
    val date: Long,
    val price: Double,
)
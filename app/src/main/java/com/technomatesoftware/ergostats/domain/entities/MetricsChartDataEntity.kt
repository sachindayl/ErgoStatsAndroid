package com.technomatesoftware.ergostats.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "metrics_chart_data")
data class MetricsChartDataEntity(
    @ColumnInfo(name = "metrics_chart_id") @PrimaryKey(autoGenerate = true) val metricsChartId: Long = 0,
    val date: Long,
    val value: Int,
    @ColumnInfo(name = "is_p2pk") val isP2pk: Boolean = false,
    @ColumnInfo(name = "is_contract") val isContract: Boolean = false,
    @ColumnInfo(name = "is_mining") val isMining: Boolean = false,
    @ColumnInfo(name = "is_volume") val isVolume: Boolean = false,
    @ColumnInfo(name = "is_transaction") val isTransaction: Boolean = false,
    @ColumnInfo(name = "is_utxo") val isUTXO: Boolean = false,
)
package com.technomatesoftware.ergostats.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "summary_metrics")
data class SummaryMetricsEntity(
    @ColumnInfo(name = "summary_metric_id") @PrimaryKey(autoGenerate = true) val summaryMetricId: Long = 0,
    val label: String,
    val current: Long,
    @ColumnInfo(name = "is_absolute") val isAbsolute: Boolean = false,
    @ColumnInfo(name = "is_relative") val isRelative: Boolean = false,
    @ColumnInfo(name = "is_utxos") val isUtxos: Boolean = false,
    @ColumnInfo(name = "is_volume") val isVolume: Boolean = false,
    @ColumnInfo(name = "is_transactions") val isTransactions: Boolean = false,
    @ColumnInfo(name = "is_miners") val isMiners: Boolean = false,
    @ColumnInfo(name = "is_contracts") val isContracts: Boolean = false,
    @ColumnInfo(name = "is_p2pks") val isP2pks: Boolean = false,
    @ColumnInfo(name = "diff_1d") val diff1d: Long,
    @ColumnInfo(name = "diff_1w") val diff1w: Long,
    @ColumnInfo(name = "diff_4w") val diff4w: Long,
    @ColumnInfo(name = "diff_6m") val diff6m: Long,
    @ColumnInfo(name = "diff_1y") val diff1y: Long,
)
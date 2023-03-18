package com.technomatesoftware.ergostats.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technomatesoftware.ergostats.domain.entities.SummaryMetricsEntity

@Dao
interface ErgoWatchDao {
    @Query("DELETE FROM summary_metrics WHERE is_utxos = :isUtxos")
    suspend fun clearSummaryUTXOData(isUtxos: Boolean = true)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummaryUTXOData(metrics: List<SummaryMetricsEntity>)

    @Query("SELECT * FROM summary_metrics WHERE is_utxos = :isUtxos")
    suspend fun getSummaryUTXOData(isUtxos: Boolean = true): List<SummaryMetricsEntity>
}
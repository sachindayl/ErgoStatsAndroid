package com.technomatesoftware.ergostats.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technomatesoftware.ergostats.domain.entities.MetricsChartDataEntity
import com.technomatesoftware.ergostats.domain.entities.SummaryMetricsEntity

@Dao
interface ErgoWatchDao {
    @Query("DELETE FROM summary_metrics WHERE is_utxos = :isUtxos")
    suspend fun clearSummaryUTXOData(isUtxos: Boolean = true)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetricsData(metrics: List<SummaryMetricsEntity>)

    @Query("SELECT * FROM summary_metrics WHERE is_utxos = :isUtxos")
    suspend fun getSummaryUTXOData(isUtxos: Boolean = true): List<SummaryMetricsEntity>

    @Query("DELETE FROM summary_metrics WHERE is_p2pks = :isP2pks AND is_relative = :isRelative")
    suspend fun clearSummaryP2pkData(
        isP2pks: Boolean = true,
        isRelative: Boolean = false,
    )

    @Query("SELECT * FROM summary_metrics WHERE is_p2pks = :isP2pks AND is_relative = :isRelative ")
    suspend fun getSummaryP2pkData(
        isP2pks: Boolean = true,
        isRelative: Boolean = false,
    ): List<SummaryMetricsEntity>

    @Query("DELETE FROM summary_metrics WHERE is_volume = :isVolume")
    suspend fun clearSummaryVolumeData(
        isVolume: Boolean = true,
    )

    @Query("SELECT * FROM summary_metrics WHERE is_volume = :isVolume")
    suspend fun getSummaryVolumeData(
        isVolume: Boolean = true,
    ): List<SummaryMetricsEntity>

    @Query("DELETE FROM summary_metrics WHERE is_transactions = :isTransactions")
    suspend fun clearSummaryTransactionsData(isTransactions: Boolean = true)

    @Query("SELECT * FROM summary_metrics WHERE is_transactions = :isTransactions")
    suspend fun getSummaryTransactionsData(isTransactions: Boolean = true): List<SummaryMetricsEntity>

    @Query("DELETE FROM summary_metrics WHERE is_miners = :isMiners")
    suspend fun clearSummaryMinersData(isMiners: Boolean = true)

    @Query("SELECT * FROM summary_metrics WHERE is_miners = :isMiners")
    suspend fun getSummaryMinersData(isMiners: Boolean = true): List<SummaryMetricsEntity>

    @Query("DELETE FROM summary_metrics WHERE is_contracts = :isContracts AND is_relative = :isRelative")
    suspend fun clearSummaryContractsData(
        isContracts: Boolean = true,
        isRelative: Boolean = false,
    )

    @Query("SELECT * FROM summary_metrics WHERE is_contracts = :isContracts AND is_relative = :isRelative")
    suspend fun getSummaryContractsData(
        isContracts: Boolean = true,
        isRelative: Boolean = false,
    ): List<SummaryMetricsEntity>

    @Query("DELETE FROM summary_metrics WHERE is_contracts = :isContracts AND is_relative = :isRelative")
    suspend fun clearSupplyDistributionContractsData(
        isContracts: Boolean = true,
        isRelative: Boolean = true,
    )

    @Query("SELECT * FROM summary_metrics WHERE is_contracts = :isContracts AND is_relative = :isRelative")
    suspend fun getSupplyDistributionContractsData(
        isContracts: Boolean = true,
        isRelative: Boolean = true,
    ): List<SummaryMetricsEntity>

    @Query("DELETE FROM summary_metrics WHERE is_p2pks = :isP2pks AND is_relative = :isRelative")
    suspend fun clearSupplyDistributionP2pkData(
        isP2pks: Boolean = true,
        isRelative: Boolean = true,
    )

    @Query("SELECT * FROM summary_metrics WHERE is_p2pks = :isP2pks AND is_relative = :isRelative")
    suspend fun getSupplyDistributionP2pkData(
        isP2pks: Boolean = true,
        isRelative: Boolean = true,
    ): List<SummaryMetricsEntity>

    @Query(
        "SELECT * FROM metrics_chart_data " +
                "WHERE is_p2pk = :isP2pk " +
                "AND is_contract = :isContract " +
                "AND is_mining = :isMining " +
                "AND is_volume = :isVolume " +
                "AND is_transaction = :isTransaction " +
                "AND is_utxo = :isUTXO"
    )
    suspend fun getMetricsChartData(
        isP2pk: Boolean = false,
        isContract: Boolean = false,
        isMining: Boolean = false,
        isVolume: Boolean = false,
        isTransaction: Boolean = false,
        isUTXO: Boolean = false,
    ): List<MetricsChartDataEntity>

    @Query(
        "DELETE FROM metrics_chart_data " +
                "WHERE is_p2pk = :isP2pk " +
                "AND is_contract = :isContract " +
                "AND is_mining = :isMining " +
                "AND is_volume = :isVolume " +
                "AND is_transaction = :isTransaction " +
                "AND is_utxo = :isUTXO"
    )
    suspend fun clearMetricsChartData(
        isP2pk: Boolean = false,
        isContract: Boolean = false,
        isMining: Boolean = false,
        isVolume: Boolean = false,
        isTransaction: Boolean = false,
        isUTXO: Boolean = false,
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetricsChartData(metrics: List<MetricsChartDataEntity>)
}
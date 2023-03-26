package com.technomatesoftware.ergostats.network.interfaces

import com.technomatesoftware.ergostats.domain.models.AddressChartDataModel
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel
import com.technomatesoftware.ergostats.domain.models.SupplyDistributionModel
import com.technomatesoftware.ergostats.domain.models.UTXOChartDataModel
import com.technomatesoftware.ergostats.domain.models.UsageChartDataModel
import kotlinx.coroutines.flow.Flow

interface ErgoWatchRepository {
    suspend fun fetchSummaryUTXOS(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun fetchSummaryVolume(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun fetchSummaryTransactions(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun fetchSummaryMiners(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun fetchSummaryContracts(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun fetchSummaryP2pk(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun fetchSupplyDistributionP2pk(): Flow<Response<SupplyDistributionModel>>
    suspend fun fetchSupplyDistributionContracts(): Flow<Response<SupplyDistributionModel>>
    suspend fun replaceSummaryUTXOS(metrics: List<SummaryMetricsModel>)
    suspend fun fetchStoredSummaryUTXOS(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun replaceSummaryP2pk(metrics: List<SummaryMetricsModel>)
    suspend fun fetchStoredSummaryP2pk(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun replaceSummaryVolume(metrics: List<SummaryMetricsModel>)
    suspend fun fetchStoredSummaryVolume(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun replaceSummaryTransactions(metrics: List<SummaryMetricsModel>)
    suspend fun fetchStoredSummaryTransactions(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun replaceSummaryMiners(metrics: List<SummaryMetricsModel>)
    suspend fun fetchStoredSummaryMiners(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun replaceSummaryContracts(metrics: List<SummaryMetricsModel>)
    suspend fun fetchStoredSummaryContracts(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun replaceSupplyDistributionP2pk(metrics: List<SummaryMetricsModel>)
    suspend fun fetchStoredSupplyDistributionP2pk(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun replaceSupplyDistributionContracts(metrics: List<SummaryMetricsModel>)
    suspend fun fetchStoredSupplyDistributionContracts(): Flow<Response<List<SummaryMetricsModel>>>
    suspend fun fetchSummaryP2pkChartData(): Flow<Response<AddressChartDataModel>>
    suspend fun fetchSummaryContractsChartData(): Flow<Response<AddressChartDataModel>>
    suspend fun fetchSummaryMinersChartData(): Flow<Response<AddressChartDataModel>>
    suspend fun fetchSummaryTransactionsChartData(): Flow<Response<UsageChartDataModel>>
    suspend fun fetchSummaryUTXOsChartData(): Flow<Response<UTXOChartDataModel>>
    suspend fun fetchSummaryVolumeChartData(): Flow<Response<UsageChartDataModel>>
}
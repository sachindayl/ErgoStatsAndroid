package com.technomatesoftware.ergostats.domain.interfaces

import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel
import com.technomatesoftware.ergostats.domain.models.SupplyDistributionModel
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
}
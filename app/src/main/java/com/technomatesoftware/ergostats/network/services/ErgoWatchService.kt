package com.technomatesoftware.ergostats.network.services

import com.technomatesoftware.ergostats.domain.models.AddressChartDataModel
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel
import com.technomatesoftware.ergostats.domain.models.SupplyDistributionModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ErgoWatchService {
    @GET("metrics/summary/utxos")
    suspend fun fetchSummaryUTXOS(): List<SummaryMetricsModel>

    @GET("metrics/summary/volume")
    suspend fun fetchSummaryVolume(): List<SummaryMetricsModel>

    @GET("metrics/summary/transactions")
    suspend fun fetchSummaryTransactions(): List<SummaryMetricsModel>

    @GET("metrics/summary/addresses/miners")
    suspend fun fetchSummaryMiners(): List<SummaryMetricsModel>

    @GET("metrics/summary/addresses/contracts")
    suspend fun fetchSummaryContracts(): List<SummaryMetricsModel>

    @GET("metrics/summary/addresses/p2pk")
    suspend fun fetchSummaryP2pk(): List<SummaryMetricsModel>

    @GET("metrics/summary/supply/distribution/p2pk")
    suspend fun fetchSupplyDistributionP2pk(): SupplyDistributionModel

    @GET("metrics/summary/supply/distribution/contracts")
    suspend fun fetchSupplyDistributionContracts(): SupplyDistributionModel

    @GET("metrics/addresses/p2pk")
    suspend fun fetchSummaryP2pkChartData(
        @Query("fr") startTime: Long,
        @Query("to") endTime: Long,
        @Query("r") timeWindowResolution: String,
        @Query("ergusd") priceData: Boolean,
    ): AddressChartDataModel
}
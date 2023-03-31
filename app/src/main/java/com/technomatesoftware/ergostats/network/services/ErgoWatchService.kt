package com.technomatesoftware.ergostats.network.services

import com.technomatesoftware.ergostats.domain.models.AddressChartDataModel
import com.technomatesoftware.ergostats.domain.models.RankPositionModel
import com.technomatesoftware.ergostats.domain.models.RichModel
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel
import com.technomatesoftware.ergostats.domain.models.SupplyDistributionModel
import com.technomatesoftware.ergostats.domain.models.UTXOChartDataModel
import com.technomatesoftware.ergostats.domain.models.UsageChartDataModel
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("metrics/addresses/{address_type}")
    suspend fun fetchSummaryAddressChartData(
        @Path("address_type") addressType: String,
        @Query("fr") startTime: Long,
        @Query("to") endTime: Long,
        @Query("r") timeWindowResolution: String,
        @Query("ergusd") priceData: Boolean,
    ): AddressChartDataModel

    @GET("metrics/{usage_type}")
    suspend fun fetchUsageChartData(
        @Path("usage_type") usageType: String,
        @Query("fr") startTime: Long,
        @Query("to") endTime: Long,
        @Query("r") timeWindowResolution: String,
        @Query("ergusd") priceData: Boolean,
    ): UsageChartDataModel

    @GET("metrics/utxos")
    suspend fun fetchUsageUTXOChartData(
        @Query("fr") startTime: Long,
        @Query("to") endTime: Long,
        @Query("r") timeWindowResolution: String,
        @Query("ergusd") priceData: Boolean,
    ): UTXOChartDataModel

    @GET("lists/addresses/by/balance")
    suspend fun fetchTop100RichList(@Query("limit") limit: Int): List<RichModel>

    @GET("ranking/{address}")
    suspend fun fetchAddressRanking(@Path("address") address: String): RankPositionModel
}
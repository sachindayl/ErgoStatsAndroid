package com.technomatesoftware.ergostats.network.services

import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel
import retrofit2.http.GET

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
}
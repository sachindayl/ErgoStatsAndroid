package com.technomatesoftware.ergostats.network.interfaces

import com.technomatesoftware.ergostats.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface ErgoPlatformRepository {
    suspend fun fetchSupply(): Flow<Response<Double?>>

    suspend fun fetchStoredCirculatingSupply(): Flow<Response<Double>>

    suspend fun replaceCirculatingSupply(supply: Double?)
}
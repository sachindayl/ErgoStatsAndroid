package com.technomatesoftware.ergostats.network.interfaces

import com.technomatesoftware.ergostats.domain.models.AgeUSDModel
import com.technomatesoftware.ergostats.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface TokenJayRepository {
    suspend fun fetchAgeUSDInfo(): Flow<Response<AgeUSDModel>>
}
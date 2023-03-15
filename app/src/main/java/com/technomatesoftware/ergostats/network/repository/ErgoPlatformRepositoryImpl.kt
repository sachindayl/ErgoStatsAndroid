package com.technomatesoftware.ergostats.network.repository

import android.util.Log
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.network.interfaces.ErgoPlatformRepository
import com.technomatesoftware.ergostats.network.services.ErgoPlatformService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErgoPlatformRepositoryImpl @Inject constructor(
    private val ergoPlatformService: ErgoPlatformService
) : ErgoPlatformRepository {
    override suspend fun fetchSupply(): Flow<Response<Double>> = flow {
        try {
            emit(Response.Loading)
            val result = ergoPlatformService.fetchSupply()
            emit(Response.Success(result))
        } catch (e: Exception) {
            Log.d("getCoinMarketData", e.message.toString())
            emit(Response.Failure(e))
        }

    }.flowOn(Dispatchers.IO)


}
package com.technomatesoftware.ergostats.network.repository

import android.util.Log
import com.technomatesoftware.ergostats.domain.dao.ErgoPlatformDao
import com.technomatesoftware.ergostats.domain.entities.TokenSupplyEntity
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.network.interfaces.ErgoPlatformRepository
import com.technomatesoftware.ergostats.network.services.ErgoPlatformService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErgoPlatformRepositoryImpl @Inject constructor(
    private val ergoPlatformService: ErgoPlatformService,
    private val ergoPlatformDao: ErgoPlatformDao,
) : ErgoPlatformRepository {
    override suspend fun fetchSupply(): Flow<Response<Double>> = flow {
        emit(Response.Loading)
        val result = ergoPlatformService.fetchSupply()
        emit(Response.Success(result))
    }
        .flowOn(Dispatchers.IO)
        .catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchStoredCirculatingSupply(): Flow<Response<Double>> =
        flow {
            emit(Response.Loading)
            val result = ergoPlatformDao.fetchCirculatingSupply()
            Log.d("fetchStoredCirculatingSupply", result.toString())
            emit(Response.Success(result.circulatingSupply))
        }
            .flowOn(Dispatchers.IO)
            .catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun replaceCirculatingSupply(supply: Double?) {
        ergoPlatformDao.insertCirculatingSupply(TokenSupplyEntity(1, supply ?: 0.0))
    }

}
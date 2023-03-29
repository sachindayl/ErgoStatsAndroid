package com.technomatesoftware.ergostats.network.repository

import android.util.Log
import com.technomatesoftware.ergostats.domain.models.AgeUSDModel
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.network.interfaces.TokenJayRepository
import com.technomatesoftware.ergostats.network.services.TokenJayService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenJayRepositoryImpl @Inject constructor(
    private val tokenJayService: TokenJayService,
) : TokenJayRepository {
    override suspend fun fetchAgeUSDInfo(): Flow<Response<AgeUSDModel>> =
        flow {
            emit(Response.Loading)
            val result = tokenJayService.fetchAgeUSDInfo()
            Log.d("getAgeUSDInfo", result.sigmaUSDPrice.toString())
            emit(Response.Success(result))
        }
            .flowOn(Dispatchers.IO)
            .catch { err -> emit(Response.Failure(Exception(err))) }
}
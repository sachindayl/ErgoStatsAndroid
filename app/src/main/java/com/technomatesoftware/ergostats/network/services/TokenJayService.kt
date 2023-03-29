package com.technomatesoftware.ergostats.network.services

import com.technomatesoftware.ergostats.domain.models.AgeUSDModel
import retrofit2.http.GET

interface TokenJayService {
    @GET("info")
    suspend fun fetchAgeUSDInfo(): AgeUSDModel
}
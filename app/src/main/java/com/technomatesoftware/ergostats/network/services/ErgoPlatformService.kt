package com.technomatesoftware.ergostats.network.services

import retrofit2.http.GET

interface ErgoPlatformService {
    @GET("v0/info/supply")
    suspend fun fetchSupply(): Double?
}
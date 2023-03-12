package com.technomatesoftware.ergostats.modules

import com.technomatesoftware.ergostats.domain.interfaces.CoinGeckoRepository
import com.technomatesoftware.ergostats.network.repository.CoinGeckoRepositoryImpl
import com.technomatesoftware.ergostats.network.services.CoinGeckoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // "https://api.ergo.watch/metrics/addresses/p2pk?fr=" + String(fr) + "&to=" + String(to) + "&r=24h&ergusd=false"

    // "https://api.tokenjay.app/ageusd/info"

    // "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=ergo&order=market_cap_desc&per_page=10&page=1&sparkline=true&price_change_percentage=24h"

    // "https://api.ergo.watch/metrics/summary/supply/distribution/contracts"

    // "https://api.ergoplatform.com/api/v0/info/supply"

    //

    @Provides
    @Named("ERGO_WATCH_API")
    fun provideErgoWatchAPI(): String = "https://api.ergo.watch/"

    @Provides
    @Named("ERGO_PLATFORM_API")
    fun provideErgoPlatformAPI(): String = "https://api.ergoplatform.com/api/"

    @Provides
    @Named("COIN_GECKO_API")
    fun provideCoinGeckoAPI(): String = "https://api.coingecko.com/api/v3/"

    @Provides
    @Named("TOKEN_JAY_API")
    fun provideTokenJayAPI(): String = "https://api.tokenjay.app/ageusd/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

//    @Provides
//    fun provideErgoWatchRetrofit(
//        okHttpClient: OkHttpClient,
//        @Named("ERGO_WATCH_API") webAPI: String,
//    ): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(webAPI)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//    }
//
//    @Provides
//    fun provideErgoPlatformRetrofit(
//        okHttpClient: OkHttpClient,
//        @Named("ERGO_PLATFORM_API") webAPI: String,
//    ): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(webAPI)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//    }

    @Provides
    fun provideCoinGeckoRetrofit(
        okHttpClient: OkHttpClient,
        @Named("COIN_GECKO_API") webAPI: String,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(webAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

//    @Provides
//    fun provideTokenJayRetrofit(
//        okHttpClient: OkHttpClient,
//        @Named("TOKEN_JAY_API") webAPI: String,
//    ): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(webAPI)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//    }

    @Provides
    fun provideCoinGeckoService(
        retrofit: Retrofit
    ): CoinGeckoService = retrofit.create(CoinGeckoService::class.java)

    @Provides
    fun provideCoinGeckoRepository(
        coinGeckoService: CoinGeckoService,
    ): CoinGeckoRepository = CoinGeckoRepositoryImpl(
        coinGeckoService = coinGeckoService,
    )
}
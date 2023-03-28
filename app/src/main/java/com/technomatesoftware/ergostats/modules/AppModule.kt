package com.technomatesoftware.ergostats.modules

import android.content.Context
import androidx.room.Room
import com.technomatesoftware.ergostats.config.NumberFormatter
import com.technomatesoftware.ergostats.domain.dao.CoinGeckoDao
import com.technomatesoftware.ergostats.domain.dao.ErgoPlatformDao
import com.technomatesoftware.ergostats.domain.dao.ErgoWatchDao
import com.technomatesoftware.ergostats.domain.database.ErgoStatsDB
import com.technomatesoftware.ergostats.domain.database.MIGRATION_3_4
import com.technomatesoftware.ergostats.network.interfaces.CoinGeckoRepository
import com.technomatesoftware.ergostats.network.interfaces.ErgoPlatformRepository
import com.technomatesoftware.ergostats.network.interfaces.ErgoWatchRepository
import com.technomatesoftware.ergostats.network.repository.CoinGeckoRepositoryImpl
import com.technomatesoftware.ergostats.network.repository.ErgoPlatformRepositoryImpl
import com.technomatesoftware.ergostats.network.repository.ErgoWatchRepositoryImpl
import com.technomatesoftware.ergostats.network.services.CoinGeckoService
import com.technomatesoftware.ergostats.network.services.ErgoPlatformService
import com.technomatesoftware.ergostats.network.services.ErgoWatchService
import com.technomatesoftware.ergostats.network.services.TokenJayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideNumberFormatter(): NumberFormatter {
        return NumberFormatter()
    }

    @Provides
    @Singleton
    fun provideCoinStatsDB(@ApplicationContext appContext: Context): ErgoStatsDB {
        return Room.databaseBuilder(
            appContext,
            ErgoStatsDB::class.java, "ergo-stats"
        )
            .addMigrations(MIGRATION_3_4)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("ERGO_WATCH_API")
    fun provideErgoWatchRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.ergo.watch/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("ERGO_PLATFORM_API")
    fun provideErgoPlatformRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.ergoplatform.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("COIN_GECKO_API")
    fun provideCoinGeckoRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("TOKEN_JAY_API")
    fun provideTokenJayRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.tokenjay.app/ageusd/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideCoinGeckoService(
        @Named("COIN_GECKO_API") retrofit: Retrofit
    ): CoinGeckoService = retrofit.create(CoinGeckoService::class.java)

    @Provides
    fun provideErgoWatchService(
        @Named("ERGO_WATCH_API") retrofit: Retrofit
    ): ErgoWatchService = retrofit.create(ErgoWatchService::class.java)

    @Provides
    fun provideErgoPlatformService(
        @Named("ERGO_PLATFORM_API") retrofit: Retrofit
    ): ErgoPlatformService = retrofit.create(ErgoPlatformService::class.java)

    @Provides
    fun provideTokenJayService(
        @Named("TOKEN_JAY_API") retrofit: Retrofit
    ): TokenJayService = retrofit.create(TokenJayService::class.java)

    @Provides
    fun provideCoinGeckoDao(ergoStatsDB: ErgoStatsDB): CoinGeckoDao {
        return ergoStatsDB.coinGeckoDao()
    }

    @Provides
    fun provideErgoWatchDao(ergoStatsDB: ErgoStatsDB): ErgoWatchDao {
        return ergoStatsDB.ergoWatchDao()
    }

    @Provides
    fun provideErgoPlatformDao(ergoStatsDB: ErgoStatsDB): ErgoPlatformDao {
        return ergoStatsDB.ergoPlatformDao()
    }

    @Provides
    fun provideCoinGeckoRepository(
        coinGeckoService: CoinGeckoService,
        coinGeckoDao: CoinGeckoDao
    ): CoinGeckoRepository = CoinGeckoRepositoryImpl(
        coinGeckoService,
        coinGeckoDao
    )

    @Provides
    fun provideErgoWatchRepository(
        ergoWatchService: ErgoWatchService,
        ergoWatchDao: ErgoWatchDao
    ): ErgoWatchRepository = ErgoWatchRepositoryImpl(
        ergoWatchService,
        ergoWatchDao
    )

    @Provides
    fun provideErgoPlatformRepository(
        ergoPlatformService: ErgoPlatformService,
        ergoPlatformDao: ErgoPlatformDao

    ): ErgoPlatformRepository = ErgoPlatformRepositoryImpl(
        ergoPlatformService,
        ergoPlatformDao
    )


}
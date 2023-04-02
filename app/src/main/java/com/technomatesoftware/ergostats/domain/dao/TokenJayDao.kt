package com.technomatesoftware.ergostats.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technomatesoftware.ergostats.domain.entities.AgeUSDEntity

@Dao
interface TokenJayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAgeUsdData(ageUsdData: AgeUSDEntity)

    @Query("SELECT * FROM age_usd")
    suspend fun getAgeUsdData(): AgeUSDEntity
}
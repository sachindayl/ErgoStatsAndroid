package com.technomatesoftware.ergostats.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technomatesoftware.ergostats.domain.entities.TokenSupplyEntity

@Dao
interface ErgoPlatformDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCirculatingSupply(supply: TokenSupplyEntity)

    @Query("SELECT * FROM token_supply")
    suspend fun fetchCirculatingSupply(): TokenSupplyEntity
}
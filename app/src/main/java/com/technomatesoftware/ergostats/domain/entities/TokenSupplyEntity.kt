package com.technomatesoftware.ergostats.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token_supply")
data class TokenSupplyEntity(
    @ColumnInfo(name = "token_supply_id") @PrimaryKey val tokenSupplyId: Long,
    @ColumnInfo(name = "circulating_supply") val circulatingSupply: Double,
)
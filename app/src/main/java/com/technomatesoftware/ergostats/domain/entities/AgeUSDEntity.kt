package com.technomatesoftware.ergostats.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "age_usd")
data class AgeUSDEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "reserve_ratio") val reserveRatio: Int,
    @ColumnInfo(name = "sigma_usd_price") val sigmaUSDPrice: Int,
    @ColumnInfo(name = "sigma_rsv_price") val sigmaRSVPrice: Int,
)
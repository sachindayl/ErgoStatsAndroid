package com.technomatesoftware.ergostats.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_chart_data")
data class AddressChartDataEntity(
    @ColumnInfo(name = "address_chart_id") @PrimaryKey val addressChartId: Long = 0,
    val date: Long,
    @ColumnInfo(name = "greater_than_1_erg") val greaterThan1Erg: Int,
    val isP2pk: Boolean = false,
    val isContract: Boolean = false,
    val isMining: Boolean = false,
)
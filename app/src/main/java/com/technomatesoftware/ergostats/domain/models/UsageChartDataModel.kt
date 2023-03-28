package com.technomatesoftware.ergostats.domain.models

import com.google.gson.annotations.SerializedName

data class UsageChartDataModel(
    var timestamps: List<Long>,
    @SerializedName("daily_1d")
    var daily1day: List<Long>,
)

package com.technomatesoftware.ergostats.domain.models

import com.google.gson.annotations.SerializedName

data class AddressChartDataModel(
    var timestamps: List<Long>,
    @SerializedName("ge_1")
    var greaterThan1Erg: List<Int>,
)

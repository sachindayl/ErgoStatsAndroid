package com.technomatesoftware.ergostats.domain.models

data class UTXOChartDataModel(
    var timestamps: List<Long>,
    var values: List<Int>,
)

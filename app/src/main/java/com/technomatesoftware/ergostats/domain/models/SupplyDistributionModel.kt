package com.technomatesoftware.ergostats.domain.models

data class SupplyDistributionModel(
    val absolute: List<SummaryMetricsModel>, val relative: List<SummaryMetricsModel>
)

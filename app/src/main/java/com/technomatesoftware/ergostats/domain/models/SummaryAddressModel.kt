package com.technomatesoftware.ergostats.domain.models

data class SummaryAddressModel(
    val title: String,
    val subtitle: String?,
    val value: String?,
    val dataSet: List<SummaryMetricsModel>?
)

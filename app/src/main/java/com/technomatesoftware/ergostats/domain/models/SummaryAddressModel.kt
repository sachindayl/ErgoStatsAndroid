package com.technomatesoftware.ergostats.domain.models

data class SummaryAddressModel(
    val id: MetricsRetrievalModel,
    val title: String,
    val subtitle: String?,
    val value: String?,
    val dataSet: List<SummaryMetricsModel>?
)

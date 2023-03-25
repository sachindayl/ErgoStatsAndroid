package com.technomatesoftware.ergostats.domain.states

import com.technomatesoftware.ergostats.config.EMPTY_STRING
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel

data class MetricDetailsViewState(
    val tableData: List<SummaryMetricsModel> = emptyList(),
    val dataDescription: String = EMPTY_STRING,
    val isChartVisible: Boolean = false,
    val isTableVisible: Boolean = false,
)

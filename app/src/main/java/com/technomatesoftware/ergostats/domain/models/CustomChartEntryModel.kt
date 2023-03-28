package com.technomatesoftware.ergostats.domain.models

import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

data class CustomChartEntryModel(
    val chartEntryModelProducer: ChartEntryModelProducer?,
    val bottomAxisValueFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom>?,
    val endAxisValueFormatter: AxisValueFormatter<AxisPosition.Vertical.End>? = null,
)

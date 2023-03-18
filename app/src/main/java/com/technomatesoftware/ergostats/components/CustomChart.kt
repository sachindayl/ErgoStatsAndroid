package com.technomatesoftware.ergostats.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.endAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.ChartModelProducer

@Composable
fun CustomChart(chartData: ChartEntryModelProducer?, bottomAxisLabels: AxisValueFormatter<AxisPosition.Horizontal.Bottom>?, height: Dp = 350.dp) {
    Chart(
        chart = lineChart(
            axisValuesOverrider = AxisValuesOverrider.adaptiveYValues(
                1f,
                round = false
            )
        ),
        chartModelProducer = chartData as ChartModelProducer<ChartEntryModel>,
        endAxis = endAxis(
            maxLabelCount = 5,
        ),
        bottomAxis = bottomAxisLabels?.let { formatter ->
            bottomAxis(
                valueFormatter = formatter,
            )
        },
        modifier = Modifier
            .height(height)
            .padding(top = 16.dp)
            .padding(16.dp)
    )
}
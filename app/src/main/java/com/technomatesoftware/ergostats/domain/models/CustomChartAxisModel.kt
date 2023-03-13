package com.technomatesoftware.ergostats.domain.models

import com.patrykandpatrick.vico.core.entry.ChartEntry

class CustomChartAxisModel(
    val formattedDate: String,
    override val x: Float,
    override val y: Float,
) : ChartEntry {
    override fun withY(y: Float) = CustomChartAxisModel(formattedDate, x, y)
}
package com.technomatesoftware.ergostats.domain.models

import com.google.gson.annotations.SerializedName

data class SummaryMetricsModel(
    val label: String, val current: Number,
    @SerializedName("diff_1d")
    val diff1d: Number,
    @SerializedName("diff_1w")
    val diff1w: Number,
    @SerializedName("diff_4w")
    val diff4w: Number,
    @SerializedName("diff_6m")
    val diff6m: Number,
    @SerializedName("diff_1y")
    val diff1y: Number,
)

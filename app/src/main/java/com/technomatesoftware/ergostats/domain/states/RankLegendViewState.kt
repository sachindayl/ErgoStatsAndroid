package com.technomatesoftware.ergostats.domain.states

import com.technomatesoftware.ergostats.domain.models.RankLegendModel

data class RankLegendViewState(
    val legendList: List<RankLegendModel> = emptyList(),
)
package com.technomatesoftware.ergostats.domain.states

import com.technomatesoftware.ergostats.config.EMPTY_STRING
import com.technomatesoftware.ergostats.domain.models.RankEnum

data class RankViewState(
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isInvalidAddress: Boolean = false,
    val userRankResult: RankEnum = RankEnum.NO_RANK,
    val fieldText: String = EMPTY_STRING
)
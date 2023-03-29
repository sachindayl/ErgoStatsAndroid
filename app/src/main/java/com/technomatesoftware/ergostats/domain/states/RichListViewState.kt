package com.technomatesoftware.ergostats.domain.states

import com.technomatesoftware.ergostats.domain.models.RichModel

data class RichListViewState(
    val richList: List<RichModel> = emptyList(),
    val isDataLoading: Boolean = false
)
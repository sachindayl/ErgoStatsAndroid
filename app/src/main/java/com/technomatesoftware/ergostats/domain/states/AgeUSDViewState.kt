package com.technomatesoftware.ergostats.domain.states

import com.technomatesoftware.ergostats.config.EMPTY_STRING

data class AgeUSDViewState(
    val sigmaUSDValue: String = EMPTY_STRING,
    val sigmaUSDPerErgValue: String = EMPTY_STRING,
    val sigmaReserveValue: String = EMPTY_STRING,
    val sigmaReservePerErgValue: String = EMPTY_STRING
)
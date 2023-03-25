package com.technomatesoftware.ergostats.domain.states

import com.technomatesoftware.ergostats.config.EMPTY_STRING

data class MainViewState(
    val appBarTitle: String = EMPTY_STRING,
    val hideBottomNavBar: Boolean = false,
    val enableBackButton: Boolean = false
)
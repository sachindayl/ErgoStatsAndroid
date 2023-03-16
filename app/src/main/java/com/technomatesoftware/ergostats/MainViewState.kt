package com.technomatesoftware.ergostats

import com.technomatesoftware.ergostats.config.EMPTY_STRING

data class MainViewState(val appBarTitle: String = EMPTY_STRING, val hideAppBar: Boolean = false)
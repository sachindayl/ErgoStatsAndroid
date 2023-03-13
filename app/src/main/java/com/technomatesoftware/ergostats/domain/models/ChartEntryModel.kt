package com.technomatesoftware.ergostats.domain.models

import java.time.LocalDate

data class ChartEntryModel(val date: LocalDate, val address: String, val amount: String)

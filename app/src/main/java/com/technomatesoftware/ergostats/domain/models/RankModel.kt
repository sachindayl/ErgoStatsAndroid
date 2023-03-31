package com.technomatesoftware.ergostats.domain.models

import android.util.Log

data class RankModel(val rank: Int, val address: String, val balance: Long) {
    fun getRankForTheBalance(): RankEnum {
        Log.d("getRankForTheBalance", balance.div(1000000000).toString())
        val ergBalance = balance.div(1000000000)
        return when {
            ergBalance <= 99L -> RankEnum.RECRUIT
            ergBalance in 100L..999 -> RankEnum.SECOND_LIEUTENANT
            ergBalance in 1000L..4999 -> RankEnum.FIRST_LIEUTENANT
            ergBalance in 5000L..14999 -> RankEnum.CAPTAIN
            ergBalance in 15000L..29999 -> RankEnum.MAJOR
            ergBalance in 30000L..49999 -> RankEnum.LIEUTENANT_COLONEL
            ergBalance in 50000L..99999 -> RankEnum.COLONEL
            ergBalance in 100000L..249999 -> RankEnum.BRIGADIER_GENERAL
            ergBalance in 250000L..499999 -> RankEnum.MAJOR_GENERAL
            ergBalance in 500000L..999999 -> RankEnum.LIEUTENANT_GENERAL
            ergBalance > 1000000L -> RankEnum.GENERAL
            else -> RankEnum.NO_RANK
        }
    }
}

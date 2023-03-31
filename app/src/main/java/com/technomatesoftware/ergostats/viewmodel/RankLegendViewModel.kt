package com.technomatesoftware.ergostats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technomatesoftware.ergostats.domain.models.RankLegendModel
import com.technomatesoftware.ergostats.domain.models.RankEnum
import com.technomatesoftware.ergostats.domain.states.RankLegendViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankLegendViewModel @Inject constructor() : ViewModel() {
    private val _viewState = MutableStateFlow(RankLegendViewState())
    val viewState: StateFlow<RankLegendViewState> = _viewState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            legendList()
        }
    }

    private fun legendList() {
        _viewState.value = _viewState.value.copy(
            legendList = listOf(
                RankLegendModel(RankEnum.RECRUIT, "Recruit", "0 ERG"),
                RankLegendModel(RankEnum.SECOND_LIEUTENANT, "Second Lieutenant", "100 ERG"),
                RankLegendModel(RankEnum.FIRST_LIEUTENANT, "First Lieutenant", "1,000 ERG"),
                RankLegendModel(RankEnum.CAPTAIN, "Captain", "5,000 ERG"),
                RankLegendModel(RankEnum.MAJOR, "Major", "15,000 ERG"),
                RankLegendModel(RankEnum.LIEUTENANT_COLONEL, "Lieutenant Colonel", "30,000 ERG"),
                RankLegendModel(RankEnum.COLONEL, "Colonel", "50,000 ERG"),
                RankLegendModel(RankEnum.BRIGADIER_GENERAL, "Brigadier General", "100,000 ERG"),
                RankLegendModel(RankEnum.MAJOR_GENERAL, "Major General", "250,000 ERG"),
                RankLegendModel(RankEnum.LIEUTENANT_GENERAL, "Lieutenant General", "500,000 ERG"),
                RankLegendModel(RankEnum.GENERAL, "General", "1,000,000 ERG"),
            )
        )
    }
}
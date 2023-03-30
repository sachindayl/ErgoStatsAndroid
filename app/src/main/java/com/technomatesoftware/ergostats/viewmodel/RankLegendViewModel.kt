package com.technomatesoftware.ergostats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technomatesoftware.ergostats.domain.models.RankLegendModel
import com.technomatesoftware.ergostats.domain.models.RankModel
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
                RankLegendModel(RankModel.RECRUIT, "Recruit", "0 ERG"),
                RankLegendModel(RankModel.SECOND_LIEUTENANT, "Second Lieutenant", "100 ERG"),
                RankLegendModel(RankModel.FIRST_LIEUTENANT, "First Lieutenant", "1,000 ERG"),
                RankLegendModel(RankModel.CAPTAIN, "Captain", "5,000 ERG"),
                RankLegendModel(RankModel.MAJOR, "Major", "15,000 ERG"),
                RankLegendModel(RankModel.LIEUTENANT_COLONEL, "Lieutenant Colonel", "30,000 ERG"),
                RankLegendModel(RankModel.COLONEL, "Colonel", "50,000 ERG"),
                RankLegendModel(RankModel.BRIGADIER_GENERAL, "Brigadier General", "100,000 ERG"),
                RankLegendModel(RankModel.MAJOR_GENERAL, "Major General", "250,000 ERG"),
                RankLegendModel(RankModel.LIEUTENANT_GENERAL, "Lieutenant General", "500,000 ERG"),
                RankLegendModel(RankModel.GENERAL, "General", "1,000,000 ERG"),
            )
        )
    }
}
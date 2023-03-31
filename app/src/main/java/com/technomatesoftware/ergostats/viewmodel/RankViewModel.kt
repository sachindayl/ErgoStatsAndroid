package com.technomatesoftware.ergostats.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.technomatesoftware.ergostats.config.EMPTY_STRING
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.states.RankViewState
import com.technomatesoftware.ergostats.network.interfaces.ErgoWatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RankViewModel @Inject constructor(
    private val ergoWatchRepository: ErgoWatchRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(RankViewState())
    val viewState: StateFlow<RankViewState> = _viewState

    suspend fun retrieveRanking(address: String) {
        ergoWatchRepository.fetchAddressRank(address).collect { response ->
            Log.d("Rank", "getting reposnse")
            when (response) {
                is Response.Loading -> {
                    _viewState.value =
                        _viewState.value.copy(isLoading = true, isInvalidAddress = false)
                }

                is Response.Success -> {
                    if (response.data != null) {
                        Log.d("RankCheck", response.data.target.getRankForTheBalance().value)
                        _viewState.value =
                            _viewState.value.copy(
                                isLoading = false,
                                userRankResult = response.data.target.getRankForTheBalance(),
                                isInvalidAddress = false
                            )
                    }

                }

                is Response.Failure -> {
                    Log.d("Rank response", response.e.toString())
                    _viewState.value =
                        _viewState.value.copy(isLoading = false, isInvalidAddress = true)
                }
            }
        }
    }

    fun setText(text: String?) {
        _viewState.value = _viewState.value.copy(fieldText = text ?: EMPTY_STRING)
        validateAddress(text)
    }

    private fun validateAddress(text: String?) {

        if (!text.isNullOrEmpty() && text.first() != '9') {
            _viewState.value =
                _viewState.value.copy(errorMessage = "The address should start with 9")
        } else if (!text.isNullOrEmpty() && (text.length < 51 || text.length > 51)) {
            _viewState.value =
                _viewState.value.copy(errorMessage = "The address should contain 51 characters")
        } else {
            _viewState.value = _viewState.value.copy(errorMessage = null)
        }
    }
}
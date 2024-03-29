package com.technomatesoftware.ergostats.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.states.AgeUSDViewState
import com.technomatesoftware.ergostats.network.interfaces.TokenJayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeUSDViewModel @Inject constructor(
    private val tokenJayRepository: TokenJayRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(AgeUSDViewState())
    val viewState: StateFlow<AgeUSDViewState> = _viewState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            fetchStoredAgeUsdData()
            ageUSDInfo()
        }
    }

    private suspend fun fetchStoredAgeUsdData() {
        _viewState.value = _viewState.value.copy(isLoading = true)
        tokenJayRepository.fetchStoredAgeUSDInfo().collect { storedData ->
            when (storedData) {
                is Response.Success -> {
                    if (storedData.data != null) {
                        _viewState.value = _viewState.value.copy(
                            reserveRatio = "${storedData.data.getFormattedReserveRatio()}%",
                            sigmaReserveValue = "${storedData.data.getFormattedSigmaReserve()} ERG/SigRSV",
                            sigmaReservePerErgValue = storedData.data.getFormattedSigmaReservePerErg(),
                            sigmaUSDValue = "${storedData.data.getFormattedSigmaUSD()} ERG/SigUSD",
                            sigmaUSDPerErgValue = storedData.data.getFormattedSigmaUSDPerErg(),
                            isLoading = false
                        )
                    }

                }

                is Response.Failure -> {
                    Log.d("ageUSDInfo", storedData.e.toString())
                    ageUSDInfo()
                    _viewState.value = _viewState.value.copy(
                        isLoading = false,
                        errorMessage = "Unexpected error occurred"
                    )
                }

                else -> {}
            }
        }
    }

    private suspend fun ageUSDInfo() {
        tokenJayRepository.fetchAgeUSDInfo().collect { response ->
            Log.d("ageUSDInfo", "getting response")
            when (response) {

                is Response.Success -> {

                    if (response.data != null) {
                        val ageUsdInfo = response.data
                        _viewState.value = _viewState.value.copy(
                            reserveRatio = "${ageUsdInfo.getFormattedReserveRatio()}%",
                            sigmaReserveValue = "${ageUsdInfo.getFormattedSigmaReserve()} ERG/SigRSV",
                            sigmaReservePerErgValue = ageUsdInfo.getFormattedSigmaReservePerErg(),
                            sigmaUSDValue = "${ageUsdInfo.getFormattedSigmaUSD()} ERG/SigUSD",
                            sigmaUSDPerErgValue = ageUsdInfo.getFormattedSigmaUSDPerErg(),
                            isLoading = false,
                            errorMessage = null,
                        )
                        tokenJayRepository.replaceAgeUSDInfo(ageUsdInfo)
                    }

                }

                is Response.Failure -> {
                    Log.d("ageUSDInfo", response.e.toString())
                }

                else -> {}
            }
        }
    }
}
package com.technomatesoftware.ergostats.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technomatesoftware.ergostats.domain.interfaces.ErgoWatchRepository
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.models.SummaryAddressModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetricsViewModel @Inject constructor(
    private val ergoWatchRepository: ErgoWatchRepository,
) : ViewModel() {

    private val _summaryAddressesState =
        mutableStateOf<List<SummaryAddressModel>>(emptyList())
    val summaryAddressesState: State<List<SummaryAddressModel>> = _summaryAddressesState
    private val _isSummaryAddressDataLoaded: MutableState<Response<Boolean>> = mutableStateOf(Response.Loading)
    val isSummaryAddressDataLoaded: State<Response<Boolean>> = _isSummaryAddressDataLoaded

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            summaryAddressData()
        }
    }

    private fun summaryAddressData() {
        viewModelScope.launch {
            val summaryAddressList:MutableList<SummaryAddressModel> = mutableListOf()
            ergoWatchRepository.fetchSummaryContracts().collect { response ->
                when(response) {
                    is Response.Loading -> {
                        _isSummaryAddressDataLoaded.value = Response.Loading
                    }
                    is Response.Success -> {
                      summaryAddressList.add(0, SummaryAddressModel("Contracts", response.data))
                    }
                    is Response.Failure -> {
                        _isSummaryAddressDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            ergoWatchRepository.fetchSummaryMiners().collect { response ->
                when(response) {
                    is Response.Loading -> {
                        _isSummaryAddressDataLoaded.value = Response.Loading
                    }
                    is Response.Success -> {
                        summaryAddressList.add(0, SummaryAddressModel("Miners", response.data))
                    }
                    is Response.Failure -> {
                        _isSummaryAddressDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            ergoWatchRepository.fetchSummaryP2pk().collect { response ->
                when(response) {
                    is Response.Loading -> {
                        _isSummaryAddressDataLoaded.value = Response.Loading
                    }
                    is Response.Success -> {
                        summaryAddressList.add(0, SummaryAddressModel("P2PK", response.data))
                    }
                    is Response.Failure -> {
                        _isSummaryAddressDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            _summaryAddressesState.value = summaryAddressList
            _isSummaryAddressDataLoaded.value = Response.Success(true)
        }
    }
}
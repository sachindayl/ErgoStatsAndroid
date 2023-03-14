package com.technomatesoftware.ergostats.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technomatesoftware.ergostats.config.EMPTY_STRING
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
    private val _supplyDataState =
        mutableStateOf<List<SummaryAddressModel>>(emptyList())
    val supplyDataState: State<List<SummaryAddressModel>> = _supplyDataState
    private val _usageDataState =
        mutableStateOf<List<SummaryAddressModel>>(emptyList())
    val usageDataState: State<List<SummaryAddressModel>> = _usageDataState
    private val _isSummaryAddressDataLoaded: MutableState<Response<Boolean>> =
        mutableStateOf(Response.Loading)
    val isSummaryAddressDataLoaded: State<Response<Boolean>> = _isSummaryAddressDataLoaded

    private val _isSupplyDataLoaded: MutableState<Response<Boolean>> =
        mutableStateOf(Response.Loading)
    val isSupplyDataLoaded: State<Response<Boolean>> = _isSupplyDataLoaded

    private val _isUsageDataLoaded: MutableState<Response<Boolean>> =
        mutableStateOf(Response.Loading)
    val isUsageDataLoaded: State<Response<Boolean>> = _isUsageDataLoaded

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            getSummaryAddressData()
            getSupplyDistributionData()
            getUsageData()
        }
    }

    private fun getSummaryAddressData() {
        viewModelScope.launch {
            val summaryAddressList: MutableList<SummaryAddressModel> = mutableListOf()
            ergoWatchRepository.fetchSummaryContracts().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _isSummaryAddressDataLoaded.value = Response.Loading
                    }

                    is Response.Success -> {
                        summaryAddressList.add(
                            0,
                            SummaryAddressModel(
                                title = "Contracts",
                                subtitle = "Total",
                                value = response.data?.first()?.current,
                                response.data
                            )
                        )
                    }

                    is Response.Failure -> {
                        _isSummaryAddressDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            ergoWatchRepository.fetchSummaryMiners().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _isSummaryAddressDataLoaded.value = Response.Loading
                    }

                    is Response.Success -> {
                        summaryAddressList.add(
                            0,
                            SummaryAddressModel(
                                title = "Miners",
                                subtitle = "Total",
                                value = response.data?.first()?.current,
                                response.data
                            )
                        )
                    }

                    is Response.Failure -> {
                        _isSummaryAddressDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            ergoWatchRepository.fetchSummaryP2pk().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _isSummaryAddressDataLoaded.value = Response.Loading
                    }

                    is Response.Success -> {
                        summaryAddressList.add(
                            0,
                            SummaryAddressModel(
                                title = "P2PKs",
                                subtitle = "Total",
                                response.data?.first()?.current,
                                response.data
                            )
                        )
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

    private fun getSupplyDistributionData() {
        viewModelScope.launch {
            val supplyDistributionList: MutableList<SummaryAddressModel> = mutableListOf()
            ergoWatchRepository.fetchSupplyDistributionContracts().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _isSupplyDataLoaded.value = Response.Loading
                    }

                    is Response.Success -> {
                        val distributionData = response.data?.relative
                        supplyDistributionList.add(
                            0,
                            SummaryAddressModel(
                                title = "Contracts",
                                subtitle = "Top 1%",
                                distributionData?.first()?.current,
                                distributionData
                            )
                        )
                    }

                    is Response.Failure -> {
                        _isSupplyDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            ergoWatchRepository.fetchSupplyDistributionP2pk().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _isSupplyDataLoaded.value = Response.Loading
                    }

                    is Response.Success -> {
                        val distributionData = response.data?.relative
                        supplyDistributionList.add(
                            0,
                            SummaryAddressModel(
                                "P2PKs",
                                "Top 1%",
                                distributionData?.first()?.current,
                                distributionData
                            )
                        )
                    }

                    is Response.Failure -> {
                        _isSupplyDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            _supplyDataState.value = supplyDistributionList
            _isSupplyDataLoaded.value = Response.Success(true)
        }
    }

    private fun getUsageData() {
        viewModelScope.launch {
            val usageList: MutableList<SummaryAddressModel> = mutableListOf()
            ergoWatchRepository.fetchSummaryUTXOS().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _isUsageDataLoaded.value = Response.Loading
                    }

                    is Response.Success -> {
                        usageList.add(
                            0,
                            SummaryAddressModel(
                                title = "UTXOs",
                                subtitle = "",
                                response.data?.first()?.current,
                                response.data
                            )
                        )
                    }

                    is Response.Failure -> {
                        _isUsageDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            ergoWatchRepository.fetchSummaryTransactions().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _isUsageDataLoaded.value = Response.Loading
                    }

                    is Response.Success -> {
                        usageList.add(
                            0,
                            SummaryAddressModel(
                                title = "Transactions",
                                subtitle = EMPTY_STRING,
                                response.data?.first()?.current,
                                response.data
                            )
                        )
                    }

                    is Response.Failure -> {
                        _isUsageDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            ergoWatchRepository.fetchSummaryVolume().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _isUsageDataLoaded.value = Response.Loading
                    }

                    is Response.Success -> {
                        usageList.add(
                            0,
                            SummaryAddressModel(
                                title = "Transfer Volume",
                                subtitle = EMPTY_STRING,
                                value = response.data?.first()?.current?.toInt()?.div(1000) ,
                                response.data
                            )
                        )
                    }

                    is Response.Failure -> {
                        _isUsageDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            _usageDataState.value = usageList
            _isUsageDataLoaded.value = Response.Success(true)
        }
    }
}
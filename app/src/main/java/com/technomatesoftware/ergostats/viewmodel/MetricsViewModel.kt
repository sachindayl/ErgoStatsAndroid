package com.technomatesoftware.ergostats.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technomatesoftware.ergostats.config.EMPTY_STRING
import com.technomatesoftware.ergostats.config.NumberFormatter
import com.technomatesoftware.ergostats.config.TOTAL_ERGO_SUPPLY
import com.technomatesoftware.ergostats.domain.models.MetricsRetrievalModel
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.models.SummaryAddressModel
import com.technomatesoftware.ergostats.network.interfaces.ErgoPlatformRepository
import com.technomatesoftware.ergostats.network.interfaces.ErgoWatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class MetricsViewModel @Inject constructor(
    private val ergoWatchRepository: ErgoWatchRepository,
    private val ergoPlatformRepository: ErgoPlatformRepository,
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
    private val _circulatingSupplyState =
        mutableStateOf<String?>(EMPTY_STRING)
    val circulatingSupplyState: State<String?> = _circulatingSupplyState
    private val _percentMinedState =
        mutableStateOf<Double?>(0.0)
    val percentMinedState: State<Double?> = _percentMinedState

    private val _isSummaryAddressDataLoaded: MutableState<Response<Boolean>> =
        mutableStateOf(Response.Loading)
    val isSummaryAddressDataLoaded: State<Response<Boolean>> = _isSummaryAddressDataLoaded
    private val _isSupplyDataLoaded: MutableState<Response<Boolean>> =
        mutableStateOf(Response.Loading)
    val isSupplyDataLoaded: State<Response<Boolean>> = _isSupplyDataLoaded
    private val _isUsageDataLoaded: MutableState<Response<Boolean>> =
        mutableStateOf(Response.Loading)
    val isUsageDataLoaded: State<Response<Boolean>> = _isUsageDataLoaded
    private val _isCirculatingSupplyLoaded: MutableState<Response<Boolean>> =
        mutableStateOf(Response.Loading)
    val isCirculatingSupplyLoaded: State<Response<Boolean>> = _isCirculatingSupplyLoaded

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            getSummaryAddressData()
            getSupplyDistributionData()
            getUsageData()
            getCirculatingSupply()
        }
    }

    fun getTotalSupply(): String {
        val formatter = DecimalFormat("#,###")
        return "${formatter.format(TOTAL_ERGO_SUPPLY)} ERG"
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
                                id = MetricsRetrievalModel.ADDRESS_CONTRACTS,
                                title = "Contracts",
                                subtitle = "Total",
                                value = response.data?.first()?.current?.toInt().toString(),
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
                                id = MetricsRetrievalModel.ADDRESS_MINING,
                                title = "Miners",
                                subtitle = "Total",
                                value = response.data?.first()?.current?.toInt().toString(),
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
                                id = MetricsRetrievalModel.ADDRESS_P2PK,
                                title = "P2PKs",
                                subtitle = "Total",
                                response.data?.first()?.current?.toInt().toString(),
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
            val numberFormatter = NumberFormatter()
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
                                id = MetricsRetrievalModel.SUPPLY_CONTRACTS,
                                title = "Contracts",
                                subtitle = "Top 1%",
                                numberFormatter.toPercentWithDecimals(
                                    distributionData?.first()?.current?.toDouble()?.times(100),
                                    2
                                ),
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
                                MetricsRetrievalModel.SUPPLY_P2PK,
                                "P2PKs",
                                "Top 1%",
                                numberFormatter.toPercentWithDecimals(
                                    distributionData?.first()?.current?.toDouble()?.times(100), 2
                                ),
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

            ergoWatchRepository.fetchStoredSummaryUTXOS().collect { storedData ->
                when (storedData) {
                    is Response.Loading -> {
                        _isUsageDataLoaded.value = Response.Loading
                    }

                    is Response.Success -> {
                        if (storedData.data?.isNotEmpty() == true) {
                            usageList.add(
                                0, SummaryAddressModel(
                                    id = MetricsRetrievalModel.USAGE_UTXO,
                                    title = "UTXOs",
                                    subtitle = "",
                                    storedData.data.first().current.toInt().toString(),
                                    storedData.data
                                )
                            )
                        }

                        fetchAndStoreSummaryUtxos(usageList)
                    }

                    is Response.Failure -> {
                        _isUsageDataLoaded.value =
                            Response.Failure(Exception("Unexpected Error Occurred"))
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
                                id = MetricsRetrievalModel.USAGE_TRANSACTIONS,
                                title = "Transactions",
                                subtitle = EMPTY_STRING,
                                response.data?.first()?.current?.toInt().toString(),
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
                                id = MetricsRetrievalModel.USAGE_VOLUME,
                                title = "Transfer Volume",
                                subtitle = EMPTY_STRING,
                                value = "${
                                    response.data?.first()?.current?.toDouble()?.div(1000000000)
                                        ?.toBigDecimal()
                                        ?.setScale(2, RoundingMode.HALF_UP)?.toInt()
                                } ERG",
                                dataSet = response.data
                            )
                        )
                    }

                    is Response.Failure -> {
                        _isUsageDataLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }

            _usageDataState.value = usageList

            //TODO move this to when loading data from db
            _isUsageDataLoaded.value = Response.Success(true)
        }
    }

    private suspend fun fetchAndStoreSummaryUtxos(usageList: MutableList<SummaryAddressModel>) =
        ergoWatchRepository.fetchSummaryUTXOS().collect { response ->
            when (response) {
                is Response.Success -> {
                    val index =
                        usageList.indexOfFirst { it.id == MetricsRetrievalModel.USAGE_UTXO }
                    if (index != -1) {
                        usageList[index] = SummaryAddressModel(
                            id = MetricsRetrievalModel.USAGE_UTXO,
                            title = "UTXOs",
                            subtitle = "",
                            response.data?.first()?.current?.toInt().toString(),
                            response.data
                        )
                    }
                    ergoWatchRepository.replaceSummaryUTXOS(
                        response.data ?: emptyList()
                    )
                }

                is Response.Failure -> {
                    _isUsageDataLoaded.value =
                        Response.Failure(Exception("Unexpected Error Occurred"))
                }

                else -> {}
            }
        }

    private fun getCirculatingSupply() {
        viewModelScope.launch {
            ergoPlatformRepository.fetchSupply().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _isCirculatingSupplyLoaded.value = Response.Loading
                    }

                    is Response.Success -> {
                        Log.d("getSupply", response.data.toString())
                        val circulatingSupply = response.data
                        val formatter = DecimalFormat("#,###")

                        _circulatingSupplyState.value = "${formatter.format(circulatingSupply)} ERG"
                        _isCirculatingSupplyLoaded.value = Response.Success(true)

                        _percentMinedState.value = circulatingSupply?.div(TOTAL_ERGO_SUPPLY)
                    }

                    is Response.Failure -> {
                        _isCirculatingSupplyLoaded.value = Response.Failure(Exception(""))
                    }
                }
            }
        }

    }
}
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
        mutableStateOf(Response.Success(true))
    val isCirculatingSupplyLoaded: State<Response<Boolean>> = _isCirculatingSupplyLoaded

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getStoredCirculatingSupply()
            getCirculatingNetworkSupply()
            getStoredSummaryAddressData()
            getStoredSupplyDistributionData()
            getStoredUsageData()
            getSummaryAddressNetworkData()
            getSupplyDistributionNetworkData()
            getUsageNetworkData()
        }
    }

    fun getTotalSupply(): String {
        val formatter = DecimalFormat("#,###")
        return "${formatter.format(TOTAL_ERGO_SUPPLY)} ERG"
    }

    private suspend fun getStoredSummaryAddressData() {

        _isSummaryAddressDataLoaded.value = Response.Loading
        val summaryAddressList: MutableList<SummaryAddressModel> = mutableListOf()
        ergoWatchRepository.fetchStoredSummaryContracts().collect { storedData ->
            when (storedData) {

                is Response.Success -> {
                    if (storedData.data?.isNotEmpty() == true) {
                        summaryAddressList.add(
                            0,
                            SummaryAddressModel(
                                id = MetricsRetrievalModel.ADDRESS_CONTRACTS,
                                title = "Contracts",
                                subtitle = "Total",
                                value = storedData.data.first().current.toInt().toString(),
                                storedData.data
                            )
                        )
                    }

                }

                else -> {
                    //Do Nothing
                }
            }
        }

        ergoWatchRepository.fetchStoredSummaryMiners().collect { storedData ->
            when (storedData) {

                is Response.Success -> {
                    if (storedData.data?.isNotEmpty() == true) {
                        summaryAddressList.add(
                            0,
                            SummaryAddressModel(
                                id = MetricsRetrievalModel.ADDRESS_MINING,
                                title = "Miners",
                                subtitle = "Total",
                                value = storedData.data.first().current.toInt().toString(),
                                storedData.data
                            )
                        )
                    }
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        ergoWatchRepository.fetchStoredSummaryP2pk().collect { storedData ->
            when (storedData) {

                is Response.Success -> {
                    if (storedData.data?.isNotEmpty() == true) {
                        summaryAddressList.add(
                            0,
                            SummaryAddressModel(
                                id = MetricsRetrievalModel.ADDRESS_P2PK,
                                title = "P2PKs",
                                subtitle = "Total",
                                storedData.data.first().current.toInt().toString(),
                                storedData.data
                            )
                        )
                    }
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        summaryAddressList.sortBy { it.title }
        _summaryAddressesState.value = summaryAddressList
        _isSummaryAddressDataLoaded.value = Response.Success(true)

    }

    private suspend fun getSummaryAddressNetworkData() {

        val summaryAddressList: MutableList<SummaryAddressModel> = mutableListOf()
        ergoWatchRepository.fetchSummaryContracts().collect { response ->
            when (response) {

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
                    ergoWatchRepository.replaceSummaryContracts(response.data ?: emptyList())
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        ergoWatchRepository.fetchSummaryMiners().collect { response ->
            when (response) {

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
                    ergoWatchRepository.replaceSummaryMiners(response.data ?: emptyList())
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        ergoWatchRepository.fetchSummaryP2pk().collect { response ->
            when (response) {

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
                    ergoWatchRepository.replaceSummaryP2pk(response.data ?: emptyList())
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        summaryAddressList.sortBy { it.title }
        _summaryAddressesState.value = summaryAddressList

    }

    private suspend fun getStoredSupplyDistributionData() {

        val supplyDistributionList: MutableList<SummaryAddressModel> = mutableListOf()
        val numberFormatter = NumberFormatter()
        ergoWatchRepository.fetchStoredSupplyDistributionContracts().collect { storedData ->
            when (storedData) {
                is Response.Loading -> {
                    _isSupplyDataLoaded.value = Response.Loading
                }

                is Response.Success -> {
                    if (storedData.data?.isNotEmpty() == true) {
                        val distributionData = storedData.data
                        //div 100 to get the decimals stored
                        supplyDistributionList.add(
                            0,
                            SummaryAddressModel(
                                id = MetricsRetrievalModel.SUPPLY_CONTRACTS,
                                title = "Contracts",
                                subtitle = "Top 1%",
                                numberFormatter.toPercentWithDecimals(
                                    distributionData.first().current.toDouble(),
                                    2
                                ),
                                distributionData
                            )
                        )
                    }

                }

                is Response.Failure -> {
                    _isSupplyDataLoaded.value = Response.Failure(Exception(""))
                }
            }
        }

        ergoWatchRepository.fetchStoredSupplyDistributionP2pk().collect { storedData ->
            when (storedData) {
                is Response.Loading -> {
                    _isSupplyDataLoaded.value = Response.Loading
                }

                is Response.Success -> {
                    if (storedData.data?.isNotEmpty() == true) {
                        val distributionData = storedData.data
                        supplyDistributionList.add(
                            0,
                            SummaryAddressModel(
                                MetricsRetrievalModel.SUPPLY_P2PK,
                                "P2PKs",
                                "Top 1%",
                                numberFormatter.toPercentWithDecimals(
                                    distributionData.first().current.toDouble(), 2
                                ),
                                distributionData
                            )
                        )
                    }
                }

                is Response.Failure -> {
                    _isSupplyDataLoaded.value = Response.Failure(Exception(""))
                }
            }
        }

        supplyDistributionList.sortBy { it.title }
        _supplyDataState.value = supplyDistributionList
        _isSupplyDataLoaded.value = Response.Success(true)

    }

    private suspend fun getSupplyDistributionNetworkData() {

        val supplyDistributionList: MutableList<SummaryAddressModel> = mutableListOf()
        val numberFormatter = NumberFormatter()
        ergoWatchRepository.fetchSupplyDistributionContracts().collect { response ->
            when (response) {

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

                    ergoWatchRepository.replaceSupplyDistributionContracts(
                        distributionData ?: emptyList()
                    )
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        ergoWatchRepository.fetchSupplyDistributionP2pk().collect { response ->
            when (response) {

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
                    ergoWatchRepository.replaceSupplyDistributionP2pk(
                        distributionData ?: emptyList()
                    )
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        supplyDistributionList.sortBy { it.title }
        _supplyDataState.value = supplyDistributionList

    }

    private suspend fun getStoredUsageData() {
        val usageList: MutableList<SummaryAddressModel> = mutableListOf()
        ergoWatchRepository.fetchStoredSummaryUTXOS().collect { storedData ->

            when (storedData) {
                is Response.Success -> {
                    if (storedData.data?.isNotEmpty() == true) {
                        usageList.add(
                            0, SummaryAddressModel(
                                id = MetricsRetrievalModel.USAGE_UTXO,
                                title = "UTXOs",
                                subtitle = EMPTY_STRING,
                                storedData.data.first().current.toInt().toString(),
                                storedData.data
                            )
                        )
                    }
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        ergoWatchRepository.fetchStoredSummaryVolume().collect { storedData ->
            when (storedData) {
                is Response.Success -> {
                    if (storedData.data?.isNotEmpty() == true) {
                        usageList.add(
                            0, SummaryAddressModel(
                                id = MetricsRetrievalModel.USAGE_VOLUME,
                                title = "Transfer Volume",
                                subtitle = EMPTY_STRING,
                                "${
                                    storedData.data.first().current.toDouble().div(1000000000)
                                        .toBigDecimal()
                                        .setScale(2, RoundingMode.HALF_UP)?.toInt()
                                } ERG",
                                storedData.data
                            )
                        )
                    }
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        ergoWatchRepository.fetchStoredSummaryTransactions().collect { storedData ->
            when (storedData) {
                is Response.Success -> {
                    if (storedData.data?.isNotEmpty() == true) {
                        usageList.add(
                            0, SummaryAddressModel(
                                id = MetricsRetrievalModel.USAGE_TRANSACTIONS,
                                title = "Transactions",
                                subtitle = EMPTY_STRING,
                                storedData.data.first().current.toInt().toString(),
                                storedData.data
                            )
                        )
                    }
                }

                else -> {
                    //Do Nothing
                }
            }
        }
        usageList.sortBy { it.title }
        _isUsageDataLoaded.value = Response.Success(true)
        _usageDataState.value = usageList
    }

    private suspend fun getUsageNetworkData() {
        val usageList: MutableList<SummaryAddressModel> = mutableListOf()
        ergoWatchRepository.fetchSummaryUTXOS().collect { response ->

            when (response) {
                is Response.Success -> {
                    usageList.add(
                        0, SummaryAddressModel(
                            id = MetricsRetrievalModel.USAGE_UTXO,
                            title = "UTXOs",
                            subtitle = EMPTY_STRING,
                            response.data?.first()?.current?.toInt().toString(),
                            response.data
                        )
                    )
                    ergoWatchRepository.replaceSummaryUTXOS(response.data ?: emptyList())
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        ergoWatchRepository.fetchSummaryVolume().collect { response ->
            when (response) {
                is Response.Success -> {
                    usageList.add(
                        SummaryAddressModel(
                            id = MetricsRetrievalModel.USAGE_VOLUME,
                            title = "Transfer Volume",
                            subtitle = EMPTY_STRING,
                            "${
                                response.data?.first()?.current?.toDouble()?.div(1000000000)
                                    ?.toBigDecimal()
                                    ?.setScale(2, RoundingMode.HALF_UP)?.toInt()
                            } ERG",
                            response.data
                        )
                    )

                    ergoWatchRepository.replaceSummaryVolume(response.data ?: emptyList())
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        ergoWatchRepository.fetchSummaryTransactions().collect { response ->
            when (response) {
                is Response.Success -> {
                    usageList.add(
                        SummaryAddressModel(
                            id = MetricsRetrievalModel.USAGE_TRANSACTIONS,
                            title = "Transactions",
                            subtitle = EMPTY_STRING,
                            response.data?.first()?.current?.toInt().toString(),
                            response.data
                        )
                    )

                    ergoWatchRepository.replaceSummaryTransactions(response.data ?: emptyList())
                }

                else -> {
                    //Do Nothing
                }
            }
        }

        usageList.sortBy { it.title }
        _usageDataState.value = usageList
    }

    private suspend fun getStoredCirculatingSupply() {
        ergoPlatformRepository.fetchStoredCirculatingSupply().collect { storedData ->
            when (storedData) {
                is Response.Loading -> {
                    _isCirculatingSupplyLoaded.value = Response.Loading
                }

                is Response.Success -> {
                    Log.d("getSupply", storedData.data.toString())
                    if (storedData.data != null) {
                        _isCirculatingSupplyLoaded.value = Response.Success(true)
                        val circulatingSupply = storedData.data
                        val formatter = DecimalFormat("#,###")

                        _circulatingSupplyState.value =
                            "${formatter.format(circulatingSupply)} ERG"
                        _percentMinedState.value = circulatingSupply.div(TOTAL_ERGO_SUPPLY)
                    }
                }

                is Response.Failure -> {
                    _isCirculatingSupplyLoaded.value =
                        Response.Failure(Exception("Unexpected Error Occurred"))
                }
            }
        }


    }

    private suspend fun getCirculatingNetworkSupply() {

        ergoPlatformRepository.fetchSupply().collect { response ->
            when (response) {

                is Response.Success -> {
                    Log.d("getSupply", response.data.toString())
                    val circulatingSupply = response.data
                    val formatter = DecimalFormat("#,###")

                    _circulatingSupplyState.value = "${formatter.format(circulatingSupply)} ERG"
                    _percentMinedState.value = circulatingSupply?.div(TOTAL_ERGO_SUPPLY)
                    _isCirculatingSupplyLoaded.value = Response.Success(true)
                    ergoPlatformRepository.replaceCirculatingSupply(response.data)
                }

                else -> {
                    //Do Nothing
                }
            }
        }
    }


}
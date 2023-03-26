package com.technomatesoftware.ergostats.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.technomatesoftware.ergostats.config.NumberFormatter
import com.technomatesoftware.ergostats.domain.models.CustomChartAxisModel
import com.technomatesoftware.ergostats.domain.models.CustomChartEntryModel
import com.technomatesoftware.ergostats.domain.models.MetricsRetrievalModel
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel
import com.technomatesoftware.ergostats.domain.states.MetricDetailsViewState
import com.technomatesoftware.ergostats.network.interfaces.ErgoWatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MetricDetailsViewModel @Inject constructor(
    private val ergoWatchRepository: ErgoWatchRepository,
    val numberFormatter: NumberFormatter
) : ViewModel() {
    private val _viewState = MutableStateFlow(MetricDetailsViewState())
    val viewState: StateFlow<MetricDetailsViewState> = _viewState
    private val _metricTypeState =
        mutableStateOf(MetricsRetrievalModel.ADDRESS_P2PK)
    private val dateFilterModulus = 6
//    val metricTypeState: State<MetricsRetrievalModel> = _metricTypeState


    fun setMetricType(metricType: MetricsRetrievalModel?) {
        _metricTypeState.value = metricType ?: MetricsRetrievalModel.ADDRESS_P2PK
    }


    fun retrieveMetricData() {
        viewModelScope.launch {
            when (_metricTypeState.value) {
                MetricsRetrievalModel.ADDRESS_P2PK -> {
                    ergoWatchRepository.fetchStoredSummaryP2pk()
                        .collect { storedData ->
                            setTableData(storedData)
                        }
                }

                MetricsRetrievalModel.ADDRESS_CONTRACTS -> {
                    ergoWatchRepository.fetchStoredSummaryContracts()
                        .collect { storedData ->
                            setTableData(storedData)
                        }
                }

                MetricsRetrievalModel.ADDRESS_MINING -> {
                    ergoWatchRepository.fetchStoredSummaryMiners()
                        .collect { storedData ->
                            setTableData(storedData)
                        }
                }

                MetricsRetrievalModel.SUPPLY_P2PK -> {
                    ergoWatchRepository.fetchStoredSupplyDistributionP2pk()
                        .collect { storedData ->
                            setTableData(storedData)
                        }
                }

                MetricsRetrievalModel.SUPPLY_CONTRACTS -> {
                    ergoWatchRepository.fetchStoredSupplyDistributionContracts()
                        .collect { storedData ->
                            setTableData(storedData)
                        }
                }

                MetricsRetrievalModel.USAGE_VOLUME -> {
                    ergoWatchRepository.fetchStoredSummaryVolume().collect { storedData ->
                        setTableData(storedData)
                    }
                }

                MetricsRetrievalModel.USAGE_TRANSACTIONS -> {
                    ergoWatchRepository.fetchStoredSummaryTransactions().collect { storedData ->
                        setTableData(storedData)
                    }
                }

                MetricsRetrievalModel.USAGE_UTXO -> {
                    ergoWatchRepository.fetchStoredSummaryUTXOS().collect { storedData ->
                        setTableData(storedData)
                    }
                }
            }
        }

    }

    private fun setTableData(storedData: Response<List<SummaryMetricsModel>>) {
        when (storedData) {
            is Response.Success -> {
                _viewState.value = _viewState.value.copy(
                    tableData = storedData.data ?: emptyList()
                )
            }

            else -> {}
        }
    }

    fun getDataDescription() {
        val dataDescription = when (_metricTypeState.value) {
            MetricsRetrievalModel.ADDRESS_P2PK -> "Unique P2PK addresses with minimum balance 1ERG/30 days"
            MetricsRetrievalModel.ADDRESS_CONTRACTS -> "Unique contract addresses with minimum balance 1ERG/30 days"
            MetricsRetrievalModel.ADDRESS_MINING -> "Unique mining contract addresses with minimum balance 1ERG/30 days"
            MetricsRetrievalModel.SUPPLY_P2PK -> "ERG Supply in top x wallet (P2PK) addresses. Excludes contract addresses and known main exchange addresses"
            MetricsRetrievalModel.SUPPLY_CONTRACTS -> "ERG Supply in top x contract addresses. Excludes (re)emission contracts, mining contracts and EF treasury"
            MetricsRetrievalModel.USAGE_VOLUME -> "The amount of ERG transferred between different addresses over the past 30 days. Does not include coinbase emission"
            MetricsRetrievalModel.USAGE_TRANSACTIONS -> "The number of transactions per day over the past 30 days"
            MetricsRetrievalModel.USAGE_UTXO -> "The number of UTXO boxes per day over the past 30 days"
        }
        _viewState.value = _viewState.value.copy(dataDescription = dataDescription)
    }

    fun getChartVisibility() {
        val isChartVisible = when (_metricTypeState.value) {
            MetricsRetrievalModel.ADDRESS_P2PK -> true
            MetricsRetrievalModel.ADDRESS_CONTRACTS -> true
            MetricsRetrievalModel.ADDRESS_MINING -> true
            MetricsRetrievalModel.SUPPLY_P2PK -> false
            MetricsRetrievalModel.SUPPLY_CONTRACTS -> false
            MetricsRetrievalModel.USAGE_VOLUME -> true
            MetricsRetrievalModel.USAGE_TRANSACTIONS -> true
            MetricsRetrievalModel.USAGE_UTXO -> true
        }
        _viewState.value = _viewState.value.copy(isChartVisible = isChartVisible)
    }

    fun getTableVisibility() {
        val isTableVisible = when (_metricTypeState.value) {
            MetricsRetrievalModel.ADDRESS_P2PK -> true
            MetricsRetrievalModel.ADDRESS_CONTRACTS -> true
            MetricsRetrievalModel.ADDRESS_MINING -> true
            MetricsRetrievalModel.SUPPLY_P2PK -> true
            MetricsRetrievalModel.SUPPLY_CONTRACTS -> true
            MetricsRetrievalModel.USAGE_VOLUME -> false
            MetricsRetrievalModel.USAGE_TRANSACTIONS -> false
            MetricsRetrievalModel.USAGE_UTXO -> false
        }
        _viewState.value = _viewState.value.copy(isTableVisible = isTableVisible)
    }

    fun fetchChartData() {
        when (_metricTypeState.value) {
            MetricsRetrievalModel.ADDRESS_P2PK -> fetchSummaryP2pkNetworkChartData()
            MetricsRetrievalModel.ADDRESS_CONTRACTS -> fetchSummaryContractsNetworkChartData()
            MetricsRetrievalModel.ADDRESS_MINING -> fetchSummaryMinersNetworkChartData()
            MetricsRetrievalModel.USAGE_TRANSACTIONS -> fetchSummaryTransactionsNetworkChartData()
            MetricsRetrievalModel.USAGE_VOLUME -> fetchSummaryVolumeNetworkChartData()
            MetricsRetrievalModel.USAGE_UTXO -> fetchSummaryUTXOsNetworkChartData()
            else -> {
                setChartData(null)
            }
        }
    }

    private fun fetchSummaryP2pkNetworkChartData() {
        viewModelScope.launch {
            ergoWatchRepository.fetchSummaryP2pkChartData().collect { response ->

                when (response) {

                    is Response.Success -> {
                        if (response.data?.timestamps?.isNotEmpty() == true && response.data.greaterThan1Erg.isNotEmpty()) {
                            val dataWithTimestamps = mutableListOf<List<Double>>()
                            val timeStampsList = response.data.timestamps.reversed()
                            val dataList = response.data.greaterThan1Erg.reversed()
                            timeStampsList.forEachIndexed { index, timestamp ->
                                if (index == timeStampsList.size - 1 || (index + 1) % dateFilterModulus == 0) {
                                    dataWithTimestamps.add(
                                        0, mutableListOf(
                                            timestamp.toDouble(),
                                            dataList[index].toDouble()
                                        )
                                    )
                                }

                            }
                            setChartData(
                                CustomChartEntryModel(
                                    chartEntryModelProducer = produceChartEntryModel(dataSet = dataWithTimestamps),
                                    bottomAxisValueFormatter = buildChartBottomAxisValues(),
                                )
                            )

                        }

                    }

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    private fun fetchSummaryContractsNetworkChartData() {
        viewModelScope.launch {
            ergoWatchRepository.fetchSummaryContractsChartData().collect { response ->

                when (response) {

                    is Response.Success -> {
                        if (response.data?.timestamps?.isNotEmpty() == true && response.data.greaterThan1Erg.isNotEmpty()) {
                            val dataWithTimestamps = mutableListOf<List<Double>>()
                            val timeStampsList = response.data.timestamps.reversed()
                            val dataList = response.data.greaterThan1Erg.reversed()
                            timeStampsList.forEachIndexed { index, timestamp ->
                                if (index == timeStampsList.size - 1 || (index + 1) % dateFilterModulus == 0) {
                                    dataWithTimestamps.add(
                                        0, mutableListOf(
                                            timestamp.toDouble(),
                                            dataList[index].toDouble()
                                        )
                                    )
                                }

                            }
                            setChartData(
                                CustomChartEntryModel(
                                    chartEntryModelProducer = produceChartEntryModel(dataSet = dataWithTimestamps),
                                    bottomAxisValueFormatter = buildChartBottomAxisValues(),
                                )
                            )

                        }

                    }

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    private fun fetchSummaryMinersNetworkChartData() {
        viewModelScope.launch {
            ergoWatchRepository.fetchSummaryMinersChartData().collect { response ->

                when (response) {

                    is Response.Success -> {
                        if (response.data?.timestamps?.isNotEmpty() == true && response.data.greaterThan1Erg.isNotEmpty()) {
                            val dataWithTimestamps = mutableListOf<List<Double>>()
                            val timeStampsList = response.data.timestamps.reversed()
                            val dataList = response.data.greaterThan1Erg.reversed()
                            timeStampsList.forEachIndexed { index, timestamp ->
                                if (index == timeStampsList.size - 1 || (index + 1) % dateFilterModulus == 0) {
                                    dataWithTimestamps.add(
                                        0, mutableListOf(
                                            timestamp.toDouble(),
                                            dataList[index].toDouble()
                                        )
                                    )
                                }

                            }
                            setChartData(
                                CustomChartEntryModel(
                                    chartEntryModelProducer = produceChartEntryModel(dataSet = dataWithTimestamps),
                                    bottomAxisValueFormatter = buildChartBottomAxisValues(),
                                )
                            )

                        }

                    }

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    private fun fetchSummaryTransactionsNetworkChartData() {
        viewModelScope.launch {
            ergoWatchRepository.fetchSummaryTransactionsChartData().collect { response ->

                when (response) {

                    is Response.Success -> {
                        if (response.data?.timestamps?.isNotEmpty() == true && response.data.daily1day.isNotEmpty()) {
                            val dataWithTimestamps = mutableListOf<List<Double>>()
                            val timeStampsList = response.data.timestamps.reversed()
                            val dataList = response.data.daily1day.reversed()
                            timeStampsList.forEachIndexed { index, timestamp ->
                                if (index == timeStampsList.size - 1 || (index + 1) % dateFilterModulus == 0) {
                                    dataWithTimestamps.add(
                                        0, mutableListOf(
                                            timestamp.toDouble(),
                                            dataList[index].toDouble()
                                        )
                                    )
                                }

                            }
                            setChartData(
                                CustomChartEntryModel(
                                    chartEntryModelProducer = produceChartEntryModel(dataSet = dataWithTimestamps),
                                    bottomAxisValueFormatter = buildChartBottomAxisValues(),
                                )
                            )

                        }

                    }

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    private fun fetchSummaryVolumeNetworkChartData() {
        viewModelScope.launch {
            ergoWatchRepository.fetchSummaryVolumeChartData().collect { response ->

                when (response) {

                    is Response.Success -> {
                        if (response.data?.timestamps?.isNotEmpty() == true && response.data.daily1day.isNotEmpty()) {
                            val dataWithTimestamps = mutableListOf<List<Double>>()
                            val timeStampsList = response.data.timestamps.reversed()
                            val dataList = response.data.daily1day.reversed()
                            timeStampsList.forEachIndexed { index, timestamp ->
                                if (index == timeStampsList.size - 1 || (index + 1) % dateFilterModulus == 0) {
                                    dataWithTimestamps.add(
                                        0, mutableListOf(
                                            timestamp.toDouble(),
                                            dataList[index].toDouble()
                                        )
                                    )
                                }

                            }
                            setChartData(
                                CustomChartEntryModel(
                                    chartEntryModelProducer = produceChartEntryModel(dataSet = dataWithTimestamps),
                                    bottomAxisValueFormatter = buildChartBottomAxisValues()
                                )
                            )

                        }

                    }

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    private fun fetchSummaryUTXOsNetworkChartData() {
        viewModelScope.launch {
            ergoWatchRepository.fetchSummaryUTXOsChartData().collect { response ->

                when (response) {

                    is Response.Success -> {
                        if (response.data?.timestamps?.isNotEmpty() == true && response.data.values.isNotEmpty()) {
                            val dataWithTimestamps = mutableListOf<List<Double>>()
                            val timeStampsList = response.data.timestamps.reversed()
                            val dataList = response.data.values.reversed()
                            timeStampsList.forEachIndexed { index, timestamp ->
                                if (index == timeStampsList.size - 1 || (index + 1) % dateFilterModulus == 0) {
                                    dataWithTimestamps.add(
                                        0, mutableListOf(
                                            timestamp.toDouble(),
                                            dataList[index].toDouble()
                                        )
                                    )
                                }

                            }
                            setChartData(
                                CustomChartEntryModel(
                                    chartEntryModelProducer = produceChartEntryModel(dataSet = dataWithTimestamps),
                                    bottomAxisValueFormatter = buildChartBottomAxisValues(),
                                )
                            )

                        }

                    }

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    private fun setChartData(chartConfigData: CustomChartEntryModel?) {
        _viewState.value = _viewState.value.copy(
            chartConfig = chartConfigData
        )
    }

    private fun produceChartEntryModel(dataSet: List<List<Double>>): ChartEntryModelProducer =
        dataSet.mapIndexed { index, data ->
            val date = SimpleDateFormat("MMM d", Locale.getDefault()).format(
                Date(data.first().toLong())
            )
            CustomChartAxisModel(
                date,
                index.toFloat(),
                data[1].toFloat()
            )
        }.let { ChartEntryModelProducer(it as List<ChartEntry>) }

    private fun buildChartBottomAxisValues(): AxisValueFormatter<AxisPosition.Horizontal.Bottom> =
        AxisValueFormatter { value, chartValues ->
            (chartValues.chartEntryModel.entries.first()
                .getOrNull(value.toInt()) as? CustomChartAxisModel)?.formattedDate
                .orEmpty()
        }

//    private fun buildChartEndAxisValues(): AxisValueFormatter<AxisPosition.Vertical.End> =
//        AxisValueFormatter { value, chartValues ->
//            val roundedValue = (chartValues.chartEntryModel.entries.first()
//                .getOrNull(value.toInt()) as? CustomChartAxisModel)?.y?.toInt()
//            roundedValue.toString()
//        }
//
//    private fun buildChartEndAxisValues2(): AxisValueFormatter<AxisPosition.Vertical.End> =
//        AxisValueFormatter { value, chartValues ->
//            val roundedValue = (chartValues.chartEntryModel.entries.first()
//                .getOrNull(value.toInt()) as? CustomChartAxisModel)?.y?.toLong()
//            roundedValue.toString()
//        }
//
//    private fun buildChartEndAxisValues3(): AxisValueFormatter<AxisPosition.Vertical.End> =
//        AxisValueFormatter { value, chartValues ->
//            val roundedValue = (chartValues.chartEntryModel.entries.first()
//                .getOrNull(value.toInt()) as? CustomChartAxisModel)?.y?.toLong()
//            return@AxisValueFormatter roundedValue.toString()
//        }
}
package com.technomatesoftware.ergostats.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.technomatesoftware.ergostats.domain.models.CoinMarketDataModel
import com.technomatesoftware.ergostats.domain.models.CustomChartAxisModel
import com.technomatesoftware.ergostats.domain.models.CustomChartEntryModel
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.network.interfaces.CoinGeckoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinGeckoRepository: CoinGeckoRepository,
) : ViewModel() {
    private val _coinGeckoState =
        mutableStateOf<Response<List<CoinMarketDataModel>>>(Response.Success(null))
    private val _coinGeckoChartEntryState = mutableStateOf(
        CustomChartEntryModel(ChartEntryModelProducer(), null, null)
    )
    val coinGeckoState: State<Response<List<CoinMarketDataModel>>> = _coinGeckoState
    val coinGeckoChartEntryState: State<CustomChartEntryModel> = _coinGeckoChartEntryState

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            coinMarketData()
            getCoinMarketChartData()
        }
    }

    private suspend fun coinMarketData() {
        coinGeckoRepository.getStoredCoinMarketData().collect { storedData ->

            when (storedData) {
                is Response.Success -> {
                    if (storedData.data?.isNotEmpty() == true) {
                        _coinGeckoState.value = storedData
                    }
                    coinGeckoRepository.getCoinMarketData().collect { response ->
                        when (response) {
                            is Response.Success -> {
                                _coinGeckoState.value = response
                                coinGeckoRepository.replaceCoinMarketData(
                                    response.data ?: emptyList()
                                )
                            }

                            else -> {}
                        }
                    }
                }

                else -> {}
            }
        }

    }

    private suspend fun getCoinMarketChartData() {

        coinGeckoRepository.getStoredMarketChartData().collect { storedData ->
            when (storedData) {
                is Response.Success -> {
                    if (storedData.data?.isNotEmpty() == true) {
                        val filteredList = filterChartDataSet(storedData.data)
                        _coinGeckoChartEntryState.value =
                            CustomChartEntryModel(
                                produceChartEntryModel(filteredList),
                                buildChartBottomAxisValues()
                            )
                    }
                    coinGeckoRepository.getCoinMarketPriceChartData().collect { response ->

                        when (response) {
                            is Response.Success -> {
                                val filteredList =
                                    filterChartDataSet(response.data ?: emptyList())

                                _coinGeckoChartEntryState.value =
                                    CustomChartEntryModel(
                                        produceChartEntryModel(filteredList),
                                        buildChartBottomAxisValues()
                                    )

                                coinGeckoRepository.replaceMarketChartData(
                                    response.data ?: emptyList()
                                )
                            }

                            else -> {}
                        }
                    }

                }

                else -> {}
            }
        }

    }

    private fun filterChartDataSet(
        dataSet: List<List<Double>>,
        numberOfDays: Int = 7
    ): List<List<Double>> = dataSet.filterIndexed { index, _ ->
        index == dataSet.size - 1 || (index + 1) % numberOfDays == 0
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
//                .getOrNull(value.toInt()) as? CustomChartAxisModel)?.y?.toBigDecimal()
//                ?.setScale(2, RoundingMode.HALF_UP)
//            roundedValue.toString()
//        }
}
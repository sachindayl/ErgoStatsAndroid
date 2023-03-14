package com.technomatesoftware.ergostats.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.technomatesoftware.ergostats.domain.interfaces.CoinGeckoRepository
import com.technomatesoftware.ergostats.domain.models.CoinMarketDataModel
import com.technomatesoftware.ergostats.domain.models.CustomChartAxisModel
import com.technomatesoftware.ergostats.domain.models.CustomChartEntryModel
import com.technomatesoftware.ergostats.domain.models.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.RoundingMode
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

    private fun coinMarketData() {
        viewModelScope.launch {
            coinGeckoRepository.getCoinMarketData().collect { response ->
                _coinGeckoState.value = response

                when (val coinGeckoStats = _coinGeckoState.value) {
                    is Response.Success -> {
                        val pricesList = coinGeckoStats.data?.first()?.sparklineIn7D?.price
                        val newEntries = ArrayList<Number>()
                        pricesList?.let { newEntries.addAll(it) }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getCoinMarketChartData() {
        viewModelScope.launch {
            coinGeckoRepository.getCoinMarketPriceChartData().collect { response ->

                when (response) {
                    is Response.Success -> {
                        val filteredList = response.data?.prices?.filterIndexed { index, _ ->
                            index == response.data.prices.size -1 || (index + 1) % 7 == 0
                        }

                        val chartEntryModelProducer =
                            filteredList?.mapIndexed { index, data ->
                                val date = SimpleDateFormat("MMM d", Locale.getDefault()).format(
                                    Date(data.first().toLong())
                                )
                                CustomChartAxisModel(
                                    date,
                                    index.toFloat(),
                                    data[1].toFloat()
                                )
                            }.let { ChartEntryModelProducer(it as List<ChartEntry>) }

                        val bottomAxisValueFormatter =
                            AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, chartValues ->
                                (chartValues.chartEntryModel.entries.first()
                                    .getOrNull(value.toInt()) as? CustomChartAxisModel)?.formattedDate
                                    .orEmpty()
                            }

                        val endAxisValueFormatter =
                            AxisValueFormatter<AxisPosition.Vertical.End> { value, chartValues ->

                                val roundedValue = (chartValues.chartEntryModel.entries.first()
                                    .getOrNull(value.toInt()) as? CustomChartAxisModel)?.y?.toBigDecimal()
                                    ?.setScale(2, RoundingMode.HALF_UP)
                                roundedValue.toString()
                            }

                        _coinGeckoChartEntryState.value =
                            CustomChartEntryModel(
                                chartEntryModelProducer,
                                bottomAxisValueFormatter,
                                endAxisValueFormatter
                            )
                    }

                    else -> {}
                }
            }
        }
    }
}
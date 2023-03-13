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

    fun coinMarketData() {
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

    fun getCoinMarketChartData() {
        viewModelScope.launch {
            coinGeckoRepository.getCoinMarketPriceChartData().collect { response ->
//                val chartEntryModelProducer = listOf(
//                    "2022-07-14" to 2f,
//                    "2022-07-15" to 4f,
//                    "2022-07-17" to 2f,
//                    "2022-08-01" to 8f,
//                ).mapIndexed { index, (dateString, y) -> Entry(LocalDate.parse(dateString), index.toFloat(), y) }
//                    .let { ChartEntryModelProducer(it) }
//
//                val axisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal> { value, chartValues ->
//                    (chartValues.chartEntryModel.entries.first().getOrNull(value.toInt()) as? Entry)
//                        ?.localDate
//                        ?.run { "$dayOfMonth/$monthValue" }
//                        .orEmpty()

//                val originalList = listOf("item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9", "item10",
//                    "item11", "item12", "item13", "item14", "item15", "item16", "item17", "item18", "item19", "item20",
//                    "item21", "item22", "item23", "item24", "item25", "item26", "item27", "item28", "item29", "item30")
//
//                val every7thItem = originalList.filterIndexed { index, _ -> (index + 1) % 7 == 0 }
//
//                println(every7thItem)


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
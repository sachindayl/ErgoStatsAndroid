package views.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.endAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartModelProducer
import com.technomatesoftware.ergostats.components.Heading
import com.technomatesoftware.ergostats.components.SummaryItem
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.viewmodel.HomeViewModel

@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    padding: PaddingValues
) {
    val scrollState = rememberScrollState()

    when (val coinStats = homeViewModel.coinGeckoState.value) {
        is Response.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                CircularProgressIndicator()
            }

        }

        is Response.Success -> {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start

            ) {
                coinStats.data?.first()?.let {

                    Chart(
                        chart = lineChart(
                            axisValuesOverrider = AxisValuesOverrider.adaptiveYValues(
                                1f,
                                round = false
                            )
                        ),
                        chartModelProducer = homeViewModel.coinGeckoChartEntryState.value.chartEntryModelProducer as ChartModelProducer<ChartEntryModel>,
                        endAxis = endAxis(
                            maxLabelCount = 5,
                        ),
                        bottomAxis = homeViewModel.coinGeckoChartEntryState.value.bottomAxisValueFormatter?.let { formatter ->
                            bottomAxis(
                                valueFormatter = formatter,
                            )
                        },
                        modifier = Modifier
                            .height(350.dp)
                            .padding(top = 16.dp)
                            .padding(16.dp)
                    )

                    Heading("Summary")

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 16.dp)
                    ) {
                        LazyVerticalGrid(
                            GridCells.Fixed(2),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .height(250.dp)
                                .padding(12.dp)
                        ) {
                            item {
                                SummaryItem(
                                    title = "Market Cap",
                                    value1 = it.formattedMarketCap(),
                                    value2 = it.formattedMarketCapChangePercentage24H()
                                        .toString()
                                )
                            }
                            item {
                                SummaryItem(
                                    title = "Current Price",
                                    value1 = it.formattedCurrentPrice(),
                                    value2 = it.formattedPriceChangePercentage24H().toString()
                                )
                            }
                            item {
                                SummaryItem(
                                    title = "Total Volume",
                                    value1 = it.formattedTotalVolume(),
                                )
                            }
                            item {
                                SummaryItem(
                                    title = "Market Cap Rank",
                                    value1 = it.marketCapRank?.toInt().toString()
                                )
                            }
                            item {
                                SummaryItem(
                                    title = "Low 24h",
                                    value1 = it.formattedLow24h(),
                                )
                            }
                            item {
                                SummaryItem(
                                    title = "High 24h",
                                    value1 = it.formattedHigh24h(),
                                )
                            }
                        }
                    }


                }
            }


        }

        is Response.Failure -> {


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text("Unexpected Error Occurred")
                Button(onClick = { homeViewModel.loadData() }) {
                    Text("Try Again")
                }
                Log.d("Testing", "failed")
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewHomeView() {
    Scaffold { padding ->
        HomeView(
            padding = padding
        )
    }

}
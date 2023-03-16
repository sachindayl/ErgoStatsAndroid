package com.technomatesoftware.ergostats.views.home

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.technomatesoftware.ergostats.components.CustomChart
import com.technomatesoftware.ergostats.components.Heading
import com.technomatesoftware.ergostats.components.SummaryItem
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.viewmodel.HomeViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton

@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = remember { MainViewModelSingleton.viewModel },
    padding: PaddingValues
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        mainViewModel.setTitle("Ergo Stats")
        mainViewModel.setHideBottomNavBar(false)
        mainViewModel.setEnableBackButton(false)
    }

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

                    CustomChart(
                        chartData = homeViewModel.coinGeckoChartEntryState.value.chartEntryModelProducer,
                        bottomAxisLabels = homeViewModel.coinGeckoChartEntryState.value.bottomAxisValueFormatter,
                        height = 350.dp
                    )

                    Heading(PaddingValues(horizontal = 16.dp), "Summary")

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
package com.technomatesoftware.ergostats.views.metrics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technomatesoftware.ergostats.components.Heading
import com.technomatesoftware.ergostats.components.MetricCard
import com.technomatesoftware.ergostats.components.MetricCircle
import com.technomatesoftware.ergostats.config.EMPTY_STRING
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.models.Routes
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton
import com.technomatesoftware.ergostats.viewmodel.MetricsViewModel

@Composable
fun MetricsView(
    padding: PaddingValues,
    mainViewModel: MainViewModel = remember { MainViewModelSingleton.viewModel },
    metricsViewModel: MetricsViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(Unit) {
        mainViewModel.setTitle("Metrics")
        mainViewModel.setHideBottomNavBar(false)
        mainViewModel.setEnableBackButton(false)
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start

    ) {
        AddressesList(mainViewModel, metricsViewModel, navController)
        EmissionsGrid(metricsViewModel)
        SupplyDistribution(metricsViewModel, navController)
        UsageContainer(metricsViewModel, navController)
    }

}

@Composable
fun AddressesList(
    mainViewModel: MainViewModel,
    metricsViewModel: MetricsViewModel,
    navController: NavController
) {
    Heading(PaddingValues(horizontal = 16.dp, vertical = 8.dp), "Addresses")
    when (metricsViewModel.isSummaryAddressDataLoaded.value) {
        is Response.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator()
            }

        }

        is Response.Success -> {
            LazyRow(contentPadding = PaddingValues(8.dp)) {
                items(metricsViewModel.summaryAddressesState.value) { summaryItems ->
                    MetricCard(
                        padding = PaddingValues(horizontal = 8.dp),
                        title = summaryItems.title,
                        subtitle = summaryItems.subtitle,
                        value = summaryItems.value,
                        cardDetails = summaryItems.dataSet,
                        onClick = {
                            mainViewModel.setTitle("Metrics Details")
                            navController.navigate(Routes.METRICS_DETAILS.value)
                        }
                    )
                }
            }
        }

        is Response.Failure -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Unexpected Error Occurred")
            }

        }
    }
}

@Composable
fun EmissionsGrid(metricsViewModel: MetricsViewModel) {

    Card(modifier = Modifier.padding(16.dp)) {
        when (metricsViewModel.isCirculatingSupplyLoaded.value) {
            is Response.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CircularProgressIndicator()
                }
            }

            is Response.Success -> {
                LazyVerticalGrid(
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Center,
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxSize()
                ) {
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "Emission",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            MetricCircle(metricsViewModel.percentMinedState.value)
                        }

                    }
                    item {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(4.dp), verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Max Supply", fontSize = 16.sp)
                            Text(
                                text = metricsViewModel.getTotalSupply(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(text = "Circulating Supply", fontSize = 16.sp)
                            Text(
                                text = metricsViewModel.circulatingSupplyState.value
                                    ?: EMPTY_STRING,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            is Response.Failure -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Unexpected Error Occurred")
                }
            }
        }
    }
}

@Composable
fun SupplyDistribution(metricsViewModel: MetricsViewModel, navController: NavController) {
    Heading(PaddingValues(horizontal = 16.dp, vertical = 8.dp), "Supply Distribution")
    when (metricsViewModel.isSupplyDataLoaded.value) {
        is Response.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator()
            }

        }

        is Response.Success -> {
            LazyRow(contentPadding = PaddingValues(8.dp)) {
                items(metricsViewModel.supplyDataState.value) { supplyDistribution ->
                    MetricCard(
                        padding = PaddingValues(horizontal = 8.dp),
                        title = supplyDistribution.title,
                        subtitle = supplyDistribution.subtitle,
                        value = supplyDistribution.value,
                        cardDetails = supplyDistribution.dataSet,
                        onClick = { navController.navigate(Routes.METRICS_DETAILS.value) }
                    )
                }
            }
        }

        is Response.Failure -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Unexpected Error Occurred")
            }

        }
    }
}

@Composable
fun UsageContainer(metricsViewModel: MetricsViewModel, navController: NavController) {
    Heading(PaddingValues(horizontal = 16.dp, vertical = 8.dp), "Usage")
    when (metricsViewModel.isUsageDataLoaded.value) {
        is Response.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator()
            }

        }

        is Response.Success -> {
            LazyRow(contentPadding = PaddingValues(8.dp)) {
                items(metricsViewModel.usageDataState.value) { usageData ->

                    MetricCard(
                        padding = PaddingValues(horizontal = 8.dp),
                        title = usageData.title,
                        value = usageData.value,
                        subtitle = usageData.subtitle,
                        cardDetails = usageData.dataSet,
                        onClick = { navController.navigate(Routes.METRICS_DETAILS.value) }
                    )
                }
            }
        }

        is Response.Failure -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Unexpected Error Occurred")
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewSearchView() {
    Scaffold { padding ->
        MetricsView(padding = padding, navController = rememberNavController())
    }
}
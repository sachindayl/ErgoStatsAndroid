package com.technomatesoftware.ergostats.views.metrics_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.technomatesoftware.ergostats.components.DataTable
import com.technomatesoftware.ergostats.config.EMPTY_STRING
import com.technomatesoftware.ergostats.config.NumberFormatter
import com.technomatesoftware.ergostats.domain.models.MetricsRetrievalModel
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel
import com.technomatesoftware.ergostats.domain.states.MetricDetailsViewState
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton
import com.technomatesoftware.ergostats.viewmodel.MetricDetailsViewModel

@Composable
fun MetricsDetailsView(
    padding: PaddingValues,
    metricsRetrievalId: MetricsRetrievalModel?,
    mainViewModel: MainViewModel = remember { MainViewModelSingleton.viewModel },
    metricsDetailsViewModel: MetricDetailsViewModel = hiltViewModel(),
) {
    val currentState: State<MetricDetailsViewState> =
        metricsDetailsViewModel.viewState.collectAsState()
    LaunchedEffect(Unit) {
        mainViewModel.setTitle("Metrics Details")
        mainViewModel.setHideBottomNavBar(true)
        mainViewModel.setEnableBackButton(true)
        metricsDetailsViewModel.setMetricType(metricsRetrievalId)
        metricsDetailsViewModel.retrieveMetricData()
        metricsDetailsViewModel.getDataDescription()
        metricsDetailsViewModel.getChartVisibility()
        metricsDetailsViewModel.getTableVisibility()
    }

    MetricsDetailsBody(
        padding = padding,
        dataDescription = currentState.value.dataDescription,
        tableLabels = listOf(
            EMPTY_STRING,
            "Current",
            "1 day",
            "1 week",
            "4 weeks",
            "1 year"
        ),
        tableData = currentState.value.tableData,
        isTableVisible = currentState.value.isTableVisible,
        isPercentData = metricsRetrievalId == MetricsRetrievalModel.SUPPLY_P2PK || metricsRetrievalId == MetricsRetrievalModel.SUPPLY_CONTRACTS,
        numberFormatter = metricsDetailsViewModel.numberFormatter
    )
}

@Composable
fun MetricsDetailsBody(
    padding: PaddingValues,
    dataDescription: String,
    tableLabels: List<String>,
    tableData: List<SummaryMetricsModel>,
    isTableVisible: Boolean,
    isPercentData: Boolean,
    numberFormatter: NumberFormatter
) {
    Column(Modifier.padding(padding)) {
//       CustomChart(chartData = chartEntryModelProducer, bottomAxisLabels = bottomAxisLabels)

        Text(
            dataDescription,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )

        if (isTableVisible) {
            DataTable(
                titles = tableLabels,
                data = tableData,
                isPercent = isPercentData,
                numberFormatter = numberFormatter
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MetricsDetailsViewPreview() {
    Scaffold {
        MetricsDetailsBody(
            padding = it,
            tableLabels = listOf(
                EMPTY_STRING,
                "Current",
                "1 day",
                "1 week",
                "4 weeks",
                "1 year"
            ),
            dataDescription = "This is a test description",
            tableData = listOf(
                SummaryMetricsModel(
                    label = "First",
                    current = 34.212,
                    diff1d = 1.56,
                    diff1w = 243.24,
                    diff4w = 3.55,
                    diff6m = 5.34,
                    diff1y = 4.322
                ),
                SummaryMetricsModel(
                    label = "Second",
                    current = 34.212,
                    diff1d = 1.56,
                    diff1w = 243.24,
                    diff4w = 3.55,
                    diff6m = 5.34,
                    diff1y = 4.322
                )
            ),
            isTableVisible = true,
            isPercentData = false,
            numberFormatter = NumberFormatter()
        )
    }

}
package com.technomatesoftware.ergostats.views.metrics_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.technomatesoftware.ergostats.components.DataTable
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton
import com.technomatesoftware.ergostats.viewmodel.MetricsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetricsDetailsView(
    padding: PaddingValues,
    metricId: Int?,
    mainViewModel: MainViewModel = remember { MainViewModelSingleton.viewModel },
    metricsViewModel: MetricsViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        mainViewModel.setTitle("Detail View")
        mainViewModel.setHideBottomNavBar(true)
        mainViewModel.setEnableBackButton(true)
    }

    Column(Modifier.padding(padding)) {
//       CustomChart(chartData = chartEntryModelProducer, bottomAxisLabels = bottomAxisLabels)

        DataTable(
            columnCount = 4,
            data = emptyList(),
            modifier = Modifier.verticalScroll(rememberScrollState()),
        )
    }
}

@Preview
@Composable
fun MetricsDetailsViewPreview() {
    MetricsDetailsView(
        padding = PaddingValues(horizontal = 16.dp),
        1,
        hiltViewModel(),
        hiltViewModel()
    )
}
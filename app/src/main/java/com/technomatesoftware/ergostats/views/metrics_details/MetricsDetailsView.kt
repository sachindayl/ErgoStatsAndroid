package com.technomatesoftware.ergostats.views.metrics_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetricsDetailsView(
    padding: PaddingValues,
    mainViewModel: MainViewModel = remember { MainViewModelSingleton.viewModel },
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        mainViewModel.setTitle("Detail View")
        mainViewModel.setHideBottomNavBar(true)
        mainViewModel.setEnableBackButton(true)
    }

    Column(Modifier.padding(padding)) {
        Text("Test")
    }
}
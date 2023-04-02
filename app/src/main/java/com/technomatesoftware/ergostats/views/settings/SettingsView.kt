package com.technomatesoftware.ergostats.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton

@Composable
fun SettingsView(
    padding: PaddingValues,
    mainViewModel: MainViewModel = remember { MainViewModelSingleton.viewModel },
) {
    LaunchedEffect(Unit) {
        mainViewModel.setTitle("Settings")
        mainViewModel.setHideBottomNavBar(true)
        mainViewModel.setEnableBackButton(true)
    }

    SettingsViewBody(padding = padding)
}

@Composable
fun SettingsViewBody(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState()),
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SettingsViewPreview() {
    Scaffold {
        SettingsViewBody(padding = it)
    }
}
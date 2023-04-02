package com.technomatesoftware.ergostats.views.richlist

import androidx.appcompat.R.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.technomatesoftware.ergostats.config.AppBrowser
import com.technomatesoftware.ergostats.domain.states.RichListViewState
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton
import com.technomatesoftware.ergostats.viewmodel.RichListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichListView(
    padding: PaddingValues,
    mainViewModel: MainViewModel = remember { MainViewModelSingleton.viewModel },
    richListViewModel: RichListViewModel = hiltViewModel()
) {
    val baseUrl = "https://explorer.ergoplatform.com/en/addresses"
    val currentState: State<RichListViewState> =
        richListViewModel.viewState.collectAsState()
    val context = LocalContext.current
    val appBrowser = AppBrowser()

    val listModifier = Modifier
        .padding(padding)

    LaunchedEffect(Unit) {
        mainViewModel.setTitle("Rich List")
        mainViewModel.setHideBottomNavBar(false)
        mainViewModel.setEnableBackButton(false)
    }

    if (currentState.value.isDataLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = listModifier) {
            itemsIndexed(currentState.value.richList) { index, model ->
                ListItem(
                    headlineText = {
                        Text(
                            model.getTrimmedAddress(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingText = { Text(model.getFormattedBalance()) },
                    leadingContent = {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.width(20.dp)) {
                            Text("${index + 1}")
                        }

                    },
                    modifier = Modifier.clickable {
                        appBrowser.openBrowserTab(context = context, "$baseUrl/${model.address}")
                    }
                )
                Divider()
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewSearchView() {
    Scaffold { padding ->
        RichListView(padding = padding)
    }
}
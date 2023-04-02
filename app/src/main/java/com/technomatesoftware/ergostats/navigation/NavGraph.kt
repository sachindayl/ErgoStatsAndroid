package com.technomatesoftware.ergostats.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.technomatesoftware.ergostats.components.BottomNavigationBar
import com.technomatesoftware.ergostats.domain.models.MetricsRetrievalModel
import com.technomatesoftware.ergostats.domain.models.Routes
import com.technomatesoftware.ergostats.domain.states.MainViewState
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton
import com.technomatesoftware.ergostats.views.ageusd.AgeUsdView
import com.technomatesoftware.ergostats.views.home.HomeView
import com.technomatesoftware.ergostats.views.metrics.MetricsView
import com.technomatesoftware.ergostats.views.metrics_details.MetricsDetailsView
import com.technomatesoftware.ergostats.views.rank.RankView
import com.technomatesoftware.ergostats.views.rank_legend.RankLegendView
import com.technomatesoftware.ergostats.views.richlist.RichListView
import com.technomatesoftware.ergostats.views.settings.SettingsView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController
) {
    val mainViewModel = remember { MainViewModelSingleton.viewModel }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val currentState: State<MainViewState> = mainViewModel.viewState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = currentState.value.appBarTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (currentState.value.enableBackButton) {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                Icons.Rounded.ArrowBack,
                                "back arrow",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                },
                actions = {
                    when (navBackStackEntry?.destination?.route.toString()) {
                        Routes.RANK.value -> {
                            IconButton(onClick = {
                                navController.navigate(Routes.RANK_LEGEND.value)
                            }) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Share",
                                )
                            }
                        }

                        Routes.HOME.value -> {
                            IconButton(onClick = {
                                navController.navigate(Routes.SETTINGS.value)
                            }) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "Settings",
                                )
                            }
                        }

                        else -> {

                        }
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                scrollBehavior = scrollBehavior,
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            if (!currentState.value.hideBottomNavBar) {
                BottomNavigationBar(navController = navController)
            }
        }) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME.value,
        ) {
            composable(Routes.HOME.value) {
                HomeView(padding = padding)
            }
            composable(Routes.METRICS.value) {
                MetricsView(
                    padding = padding,
                    navController = navController
                )
            }
            composable(Routes.RANK.value) {
                RankView(padding = padding)
            }
            composable(Routes.RICH_LIST.value) {
                RichListView(padding = padding)
            }
            composable(Routes.AGE_USD.value) {
                AgeUsdView(padding = padding)
            }
            composable(
                "${Routes.METRICS_DETAILS.value}/{metricsRetrievalId}",
                arguments = listOf(navArgument("metricsRetrievalId") { type = NavType.IntType })
            ) { backStackEntry ->
                val metricId = backStackEntry.arguments?.getInt("metricsRetrievalId") ?: 0
                MetricsDetailsView(
                    padding = padding,
                    metricsRetrievalId = MetricsRetrievalModel.values().getOrNull(metricId)
                )
            }

            composable(Routes.RANK_LEGEND.value) {
                RankLegendView(padding = padding)
            }

            composable(Routes.SETTINGS.value) {
                SettingsView(padding = padding)
            }
        }
    }
}

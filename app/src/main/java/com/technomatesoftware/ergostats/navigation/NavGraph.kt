package com.technomatesoftware.ergostats.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.technomatesoftware.ergostats.components.BottomNavigationBar
import com.technomatesoftware.ergostats.models.Routes
import views.ageusd.AgeUsdView
import views.home.HomeView
import views.metrics.MetricsView
import views.rank.RankView
import views.richlist.RichListView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
) {
    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME.value,
        ) {
            composable(Routes.HOME.value) { HomeView(padding = padding) }
            composable(Routes.METRICS.value) { MetricsView(padding = padding) }
            composable(Routes.RANK.value) { RankView(padding = padding) }
            composable(Routes.RICHLIST.value) { RichListView(padding = padding) }
            composable(Routes.AGEUSD.value) { AgeUsdView(padding = padding) }
        }
    }

}
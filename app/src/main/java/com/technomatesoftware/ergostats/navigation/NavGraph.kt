package com.technomatesoftware.ergostats.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.technomatesoftware.ergostats.models.Routes
import views.ageusd.AgeUsdView
import views.home.HomeView
import views.metrics.MetricsView
import views.rank.RankView
import views.richlist.RichListView

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME.value,
    ) {
        composable(Routes.HOME.value) { HomeView(navController = navController) }
        composable(Routes.METRICS.value) { MetricsView(navController = navController) }
        composable(Routes.RANK.value) { RankView(navController = navController) }
        composable(Routes.RICHLIST.value) { RichListView(navController = navController) }
        composable(Routes.AGEUSD.value) { AgeUsdView(navController = navController) }
    }
}
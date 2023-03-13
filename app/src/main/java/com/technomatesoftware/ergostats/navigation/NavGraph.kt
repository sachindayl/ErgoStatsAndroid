package com.technomatesoftware.ergostats.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.technomatesoftware.ergostats.components.BottomNavigationBar
import com.technomatesoftware.ergostats.domain.models.Routes
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
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = "Ergo Stats") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
                scrollBehavior = scrollBehavior,
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = { BottomNavigationBar(navController) }) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME.value,
        ) {
            composable(Routes.HOME.value) { HomeView(padding = padding) }
            composable(Routes.METRICS.value) { MetricsView(padding = padding) }
            composable(Routes.RANK.value) { RankView(padding = padding) }
            composable(Routes.RICH_LIST.value) { RichListView(padding = padding) }
            composable(Routes.AGE_USD.value) { AgeUsdView(padding = padding) }
        }
    }

}
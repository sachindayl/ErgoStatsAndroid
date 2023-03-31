package com.technomatesoftware.ergostats.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.technomatesoftware.ergostats.R
import com.technomatesoftware.ergostats.domain.models.Routes

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        Routes.HOME,
        Routes.METRICS,
        Routes.RANK,
        Routes.RICH_LIST,
        Routes.AGE_USD
    )

    val icons = listOf(
        R.drawable.home,
        R.drawable.chart,
        R.drawable.rank,
        R.drawable.list,
        R.drawable.money
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = icons[index]), contentDescription = item.name) },
                label = {
                    Text(
                        item.value,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                selected = currentRoute == item.value,
                onClick = {
                    navController.navigate(item.value) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }

                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomNavigationBar() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController)
}
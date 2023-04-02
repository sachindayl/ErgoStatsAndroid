package com.technomatesoftware.ergostats.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technomatesoftware.ergostats.BuildConfig
import com.technomatesoftware.ergostats.R
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton
import java.time.LocalDateTime

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsViewBody(padding: PaddingValues) {
    val year = LocalDateTime.now().year
    Column(
        Modifier
            .padding(padding)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "API Data",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        ListItem(
            headlineText = {
                Text(
                    "ERG price provided by CoinGecko",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.gecko),
                    contentDescription = "gecko"
                )
            }
        )
        Divider()
        ListItem(
            headlineText = {
                Text(
                    "Metrics provided by ergo.watch",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.watch),
                    contentDescription = "watch"
                )
            }
        )
        Divider()
        ListItem(
            headlineText = {
                Text(
                    "AgeUSD provided by tokenjay.app",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.money),
                    contentDescription = "money"
                )
            }
        )
        Divider()
        Text(
            "Development",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        ListItem(
            headlineText = {
                Text(
                    "Created by Sachinda Liyanaarachchi, $year",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.creator),
                    contentDescription = "creator"
                )
            }
        )
        Divider()
        ListItem(
            headlineText = {
                Text(
                    "This project is open source",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.github),
                    contentDescription = "github"
                )
            }
        )
        Divider()
        ListItem(
            headlineText = {
                Text(
                    "Reach out on Discord",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.discord),
                    contentDescription = "discord"
                )
            }
        )
        Divider()
        Text(
            "Privacy Policy",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        ListItem(
            headlineText = {
                Text(
                    "The privacy policy can be found on GitHub",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.policy),
                    contentDescription = "policy"
                )
            }
        )
        Divider()
        Text(
            "Version",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        ListItem(
            headlineText = {
                Text(
                    "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.developer_version),
                    contentDescription = "version"
                )
            }
        )
        Divider()
    }


//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(padding)
//            .verticalScroll(rememberScrollState()),
//    ) {
//        Text("API Data", style = MaterialTheme.typography.labelSmall)
//
//        Text("ERG price provided by CoinGecko")
//        Text("Metrics provided by ergo.watch")
//        Text("AgeUSD provided by tokenjay.app")
//        Text("Development")
//        Text("Created by Sachinda Liyanaarachchi, $year")
//        Text("Contact")
//        Text("Reach out on Discord")
//        Text("Privacy Policy")
//        Text("The privacy policy can be found on GitHub")
//        Text("Version")
//        Text("${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})")
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SettingsViewPreview() {
    Scaffold {
        SettingsViewBody(padding = it)
    }
}
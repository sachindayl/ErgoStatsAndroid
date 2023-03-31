package com.technomatesoftware.ergostats.views.rank_legend

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.technomatesoftware.ergostats.R
import com.technomatesoftware.ergostats.domain.models.RankLegendModel
import com.technomatesoftware.ergostats.domain.models.RankEnum
import com.technomatesoftware.ergostats.domain.states.RankLegendViewState
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton
import com.technomatesoftware.ergostats.viewmodel.RankLegendViewModel

@Composable
fun RankLegendView(
    padding: PaddingValues,
    mainViewModel: MainViewModel = remember { MainViewModelSingleton.viewModel },
    rankLegendViewModel: RankLegendViewModel = hiltViewModel()
) {
    val currentState: State<RankLegendViewState> =
        rankLegendViewModel.viewState.collectAsState()
    LaunchedEffect(Unit) {
        mainViewModel.setTitle("Rank Legend")
        mainViewModel.setHideBottomNavBar(true)
        mainViewModel.setEnableBackButton(true)
    }

    RankLegendBody(paddingValues = padding, currentState.value.legendList)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankLegendBody(paddingValues: PaddingValues, legendList: List<RankLegendModel>) {
    val listModifier = Modifier
        .padding(paddingValues)
    LazyColumn(modifier = listModifier) {
        itemsIndexed(legendList) { index, item ->
            ListItem(
                headlineText = {
                    Text(
                        item.label,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                leadingContent = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    ) {
                        if (item.rankType != RankEnum.RECRUIT) {
                            Image(
                                painter = painterResource(id = getRankImage(item.rankType)),
                                contentDescription = item.label
                            )
                        }
                    }

                },
                trailingContent = {
                    Text(
                        item.amount,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
            Divider()
        }
    }

}

private fun getRankImage(rank: RankEnum): Int {
    return when (rank) {
        RankEnum.RECRUIT -> R.drawable.first_lieutenant
        RankEnum.CAPTAIN -> R.drawable.captain
        RankEnum.COLONEL -> R.drawable.colonel
        RankEnum.FIRST_LIEUTENANT -> R.drawable.first_lieutenant
        RankEnum.SECOND_LIEUTENANT -> R.drawable.second_lieutenant
        RankEnum.MAJOR -> R.drawable.major
        RankEnum.MAJOR_GENERAL -> R.drawable.major_general
        RankEnum.LIEUTENANT_COLONEL -> R.drawable.lieutenant_colonel
        RankEnum.LIEUTENANT_GENERAL -> R.drawable.lieutenant_general
        RankEnum.BRIGADIER_GENERAL -> R.drawable.brigadier_general
        RankEnum.GENERAL -> R.drawable.general
        else -> 0
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RankLegendBodyPreview() {
    val legendList = listOf(
        RankLegendModel(RankEnum.LIEUTENANT_GENERAL, "Lieutenant", "100,000 ERG"),
        RankLegendModel(RankEnum.GENERAL, "General", "150,000 ERG")
    )

    Scaffold { padding ->
        RankLegendBody(paddingValues = padding, legendList = legendList)
    }
}
package com.technomatesoftware.ergostats.views.rank

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.technomatesoftware.ergostats.R
import com.technomatesoftware.ergostats.domain.models.RankEnum
import com.technomatesoftware.ergostats.domain.states.RankViewState
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton
import com.technomatesoftware.ergostats.viewmodel.RankViewModel

@Composable
fun RankView(
    padding: PaddingValues,
    mainViewModel: MainViewModel = remember { MainViewModelSingleton.viewModel },
    rankViewModel: RankViewModel = hiltViewModel()
) {
    val currentState: State<RankViewState> =
        rankViewModel.viewState.collectAsState()
    val maxChar = 51
    var buttonClicked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        mainViewModel.setTitle("Rank")
        mainViewModel.setHideBottomNavBar(false)
        mainViewModel.setEnableBackButton(false)
    }

    LaunchedEffect(buttonClicked) {
        if (buttonClicked) {
            rankViewModel.retrieveRanking(currentState.value.fieldText)
        }
    }

    RankViewBody(
        padding = padding,
        address = currentState.value.fieldText,
        isInvalidAddress = currentState.value.isInvalidAddress,
        isLoading = currentState.value.isLoading,
        onAddressChange = { if (it.length <= maxChar) rankViewModel.setText(it) },
        errorMessage = currentState.value.errorMessage,
        userRankResult = currentState.value.userRankResult,
        onSubmit = {
            buttonClicked = true
        },
        onClear = { rankViewModel.setText(null) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankViewBody(
    padding: PaddingValues,
    address: String,
    isInvalidAddress: Boolean,
    isLoading: Boolean,
    onAddressChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onClear: () -> Unit,
    userRankResult: RankEnum,
    errorMessage: String?
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Column(
        modifier = Modifier
            .padding(padding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
                .height((screenHeight * 0.55).dp)

        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                if (isInvalidAddress) {
                    Text(
                        text = "Invalid address please use a valid ERGO address",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                } else if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                } else if (userRankResult != RankEnum.NO_RANK) {
                    if (userRankResult != RankEnum.RECRUIT) {
                        Image(
                            painter = painterResource(id = getRankImage(userRankResult)),
                            contentDescription = "rank image",
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                        )
                    }

                    Text(
                        text = "Your rank is",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = userRankResult.value,
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                } else {
                    Text(
                        "Σ",
                        fontSize = 56.sp,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Paste your ERGO wallet receiving address in the text field below",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                }

            }
        }
        TextField(
            value = address,
            onValueChange = onAddressChange,
            label = { Text("Address") },
            singleLine = true,
            supportingText = {
                if (errorMessage != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                if (address.isNotEmpty()) {
                    IconButton(onClick = onClear) {
                        Icon(Icons.Default.Clear, "clear")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = onSubmit,
            enabled = errorMessage == null && address.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(
                "Submit",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

private fun getRankImage(rank: RankEnum): Int {
    return when (rank) {
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
fun PreviewSearchView() {
    var text = "This"
    Scaffold { padding ->
        RankViewBody(
            padding = padding,
            address = "addr",
            isInvalidAddress = false,
            isLoading = false,
            onAddressChange = { text = it },
            errorMessage = "Should start with 9",
            userRankResult = RankEnum.LIEUTENANT_GENERAL,
            onSubmit = {},
            onClear = {}
        )
    }
}
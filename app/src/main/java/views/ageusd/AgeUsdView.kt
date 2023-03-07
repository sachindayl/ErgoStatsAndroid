package views.ageusd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technomatesoftware.ergostats.components.AgeUsdCard
import com.technomatesoftware.ergostats.components.AgeUsdTableItem

@Composable
fun AgeUsdView(
    padding: PaddingValues
) {

    Column(
        modifier = Modifier
            .padding(padding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        AgeUsdCard(padding = PaddingValues(16.dp))
        AgeUsdCard(padding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp))
        LazyVerticalGrid(
            GridCells.Fixed(3),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.SpaceEvenly,
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier
                .height(350.dp)
                .padding(16.dp)

        ) {
            item {
                Text(
                    text = "SigUSD",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }
            item {
                Text(
                    text = "Reserve Ratio",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }
            item {
                Text(
                    text = "SigRSV",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }
            item {
                AgeUsdTableItem(
                    label = "Purchase",
                    isCorrect = true,
                    padding = PaddingValues(top = 8.dp)
                )
            }
            item {
                Text(
                    "Above",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            item {
                AgeUsdTableItem(
                    label = "Purchase",
                    isCorrect = false,
                    padding = PaddingValues(top = 8.dp)
                )
            }

            item {
                AgeUsdTableItem(
                    label = "Redeem",
                    isCorrect = true,
                    padding = PaddingValues(top = 8.dp)
                )
            }

            item {
                Text(
                    "800%",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                AgeUsdTableItem(
                    label = "Purchase",
                    isCorrect = true,
                    padding = PaddingValues(top = 8.dp)
                )
            }

            item {
                AgeUsdTableItem(
                    label = "Purchase",
                    isCorrect = true,
                    padding = PaddingValues(top = 8.dp)
                )
            }

            item {
                Text(
                    "800% >",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                AgeUsdTableItem(
                    label = "Purchase",
                    isCorrect = true,
                    padding = PaddingValues(top = 8.dp)
                )
            }

            item {
                AgeUsdTableItem(
                    label = "Redeem",
                    isCorrect = true,
                    padding = PaddingValues(top = 8.dp)
                )
            }

            item {
                Text(
                    "< 400%",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                AgeUsdTableItem(
                    label = "Redeem",
                    isCorrect = true,
                    padding = PaddingValues(top = 8.dp)
                )
            }

            item {
                AgeUsdTableItem(
                    label = "Purchase",
                    isCorrect = false,
                    padding = PaddingValues(top = 8.dp)
                )
            }

            item {
                Text(
                    "Below",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                AgeUsdTableItem(
                    label = "Purchase",
                    isCorrect = true,
                    padding = PaddingValues(top = 8.dp)
                )
            }

            item {
                AgeUsdTableItem(
                    label = "Redeem",
                    isCorrect = true,
                    padding = PaddingValues(top = 8.dp)
                )
            }

            item {
                Text(
                    "400%",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                AgeUsdTableItem(
                    label = "Redeem",
                    isCorrect = false,
                    padding = PaddingValues(top = 8.dp)
                )
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AgeUsdViewPreview() {
    Scaffold { padding ->
        AgeUsdView(padding = padding)
    }
}
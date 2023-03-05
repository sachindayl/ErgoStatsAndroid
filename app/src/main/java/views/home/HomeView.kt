package views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun HomeView(
    padding: PaddingValues
) {

    val chartEntryModel = entryModelOf(4f, 12f, 8f, 16f, 22f)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start

    ) {
        Card(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
        ) {
            Chart(
                chart = lineChart(),
                model = chartEntryModel,
                startAxis = startAxis(),
                bottomAxis = bottomAxis(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
        Text(
            "Summary",
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyVerticalGrid( GridCells.Fixed(2),
            Modifier
                .height(250.dp)
                .padding(12.dp)){
            item {
                Column(Modifier.padding(4.dp)) {
                    Text(text = "Market Cap", fontSize = 16.sp)
                    Text(text = "33.45", fontSize = 20.sp)
                    Text(text = "hello", fontSize = 16.sp)
                }
            }
            item {
                Column(Modifier.padding(4.dp)) {
                    Text(text = "Current Price", fontSize = 16.sp)
                    Text(text = "33.45", fontSize = 20.sp)
                    Text(text = "hello", fontSize = 16.sp)
                }
            }
            item {
                Column(Modifier.padding(4.dp)) {
                    Text(text = "Total Volume", fontSize = 16.sp)
                    Text(text = "33.45", fontSize = 20.sp)
                }
            }
            item {
                Column(Modifier.padding(4.dp)) {
                    Text(text = "hello", fontSize = 16.sp)
                    Text(text = "33.45", fontSize = 20.sp)
                }
            }
            item {
                Column(Modifier.padding(4.dp)) {
                    Text(text = "hello", fontSize = 16.sp)
                    Text(text = "33.45", fontSize = 20.sp)
                }
            }
            item {
                Column(Modifier.padding(4.dp)) {
                    Text(text = "hello", fontSize = 16.sp)
                    Text(text = "33.45", fontSize = 20.sp)
                }
            }
        }


        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewHomeView() {
    Scaffold { padding ->
        HomeView(padding = padding)
    }

}
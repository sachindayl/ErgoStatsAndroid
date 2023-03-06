package views.metrics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technomatesoftware.ergostats.components.Heading
import com.technomatesoftware.ergostats.components.MetricCard
import com.technomatesoftware.ergostats.components.MetricCircle

@Composable
fun MetricsView(
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start

    ) {
        Heading("Addresses")
        LazyRow(contentPadding = PaddingValues(8.dp)) {
            items(5) {
                MetricCard(padding = PaddingValues(horizontal = 8.dp))
            }
        }

        Card(modifier = Modifier.padding(16.dp)) {
            LazyVerticalGrid(
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Center,
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .height(200.dp).fillMaxSize()
            ) {
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Emission",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        MetricCircle()
                    }

                }
                item {
                    Column(Modifier.fillMaxSize().padding(4.dp), verticalArrangement = Arrangement.Center) {
                        Text(text = "Max Supply", fontSize = 16.sp)
                        Text(
                            text = "33.45 Erg",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(text = "Circulating Supply", fontSize = 16.sp)
                        Text(
                            text = "323,546,464 Erg",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }



        Heading("Supply Distribution")
        LazyRow(contentPadding = PaddingValues(8.dp)) {
            items(5) {
                MetricCard(padding = PaddingValues(horizontal = 8.dp))
            }
        }
        Heading("Usage")
        LazyRow(contentPadding = PaddingValues(8.dp)) {
            items(5) {
                MetricCard(padding = PaddingValues(horizontal = 8.dp))
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewSearchView() {
    Scaffold { padding ->
        MetricsView(padding = padding)
    }
}
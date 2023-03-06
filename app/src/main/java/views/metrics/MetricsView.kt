package views.metrics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technomatesoftware.ergostats.components.Heading
import com.technomatesoftware.ergostats.components.MetricCard

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
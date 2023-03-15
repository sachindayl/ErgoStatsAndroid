package com.technomatesoftware.ergostats.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technomatesoftware.ergostats.config.EMPTY_STRING
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel
import java.util.Locale

@Composable
fun MetricCard(
    padding: PaddingValues,
    title: String,
    subtitle: String?,
    value: String?,
    cardDetails: List<SummaryMetricsModel>? = emptyList()
) {


    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier
            .height(100.dp)
            .width(200.dp)
            .padding(padding)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            Text(value ?: EMPTY_STRING, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(subtitle ?: EMPTY_STRING, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MetricCardPreview() {
    MetricCard(PaddingValues(8.dp), "Test","Test2", "7123.3")
}



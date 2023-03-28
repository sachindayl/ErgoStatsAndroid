package com.technomatesoftware.ergostats.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technomatesoftware.ergostats.config.EMPTY_STRING
import com.technomatesoftware.ergostats.config.NumberFormatter
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel


@Composable
fun DataTable(
    titles: List<String>,
    data: List<SummaryMetricsModel>,
    isPercent: Boolean,
    numberFormatter: NumberFormatter
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            TableTitles(titles)
        }
        itemsIndexed(data) { _, row ->
            TableValues(item = row, isPercent = isPercent, numberFormatter = numberFormatter)
        }

    }
}

@Composable
fun TableTitles(titles: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        titles.forEach {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(
                    text = it,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TableValues(item: SummaryMetricsModel, isPercent: Boolean, numberFormatter: NumberFormatter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedLabel(item.label),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
            maxLines = 1,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Left
        )
        Text(
            text = if (isPercent) numberFormatter.toPercentWithDecimals(
                item.current.toDouble(),
                2
            ) else item.current.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            text = if (isPercent) numberFormatter.toPercentWithDecimals(
                item.diff1d.toDouble(),
                2
            ) else item.diff1d.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            text = if (isPercent) numberFormatter.toPercentWithDecimals(
                item.diff1w.toDouble(),
                2
            ) else item.diff1w.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            text = if (isPercent) numberFormatter.toPercentWithDecimals(
                item.diff4w.toDouble(),
                2
            ) else item.diff4w.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            text = if (isPercent) numberFormatter.toPercentWithDecimals(
                item.diff1y.toDouble(),
                2
            ) else item.diff1y.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }

}

fun formattedLabel(label: String): String {
    when (label) {
        "ge_0p001" -> {
            return "0.001"
        }

        "ge_0p01" -> {
            return "0.01"
        }

        "ge_0p1" -> {
            return "0.1"
        }

        "ge_1" -> {
            return "1"
        }

        "ge_10" -> {
            return "10"
        }

        "ge_100" -> {
            return "100"
        }

        "ge_1k" -> {
            return "1K"
        }

        "ge_10k" -> {
            return "10K"
        }

        "ge_100k" -> {
            return "100K"
        }

        "ge_1m" -> {
            return "1M"
        }

        "total" -> {
            return "Total"
        }

        "top_1_prc" -> {
            return "Top 1%"
        }

        "top_1k" -> {
            return "Top 1K"
        }

        "top_100" -> {
            return "Top 100"
        }

        "top_10" -> {
            return "Top 10"
        }

        else -> {
            return label
        }
    }
}

@Preview
@Composable
fun DataTablePreview() {
    DataTable(
        titles = listOf(
            EMPTY_STRING,
            "Current",
            "1 day",
            "1 week",
            "4 weeks",
            "1 year"
        ),
        data = listOf(
            SummaryMetricsModel(
                label = "First",
                current = 34.212,
                diff1d = 1.56,
                diff1w = 243.24,
                diff4w = 3.55,
                diff6m = 5.34,
                diff1y = 4.322
            ),
            SummaryMetricsModel(
                label = "Second",
                current = 34.212,
                diff1d = 1.56,
                diff1w = 243.24,
                diff4w = 3.55,
                diff6m = 5.34,
                diff1y = 4.322
            )
        ),
        isPercent = false,
        NumberFormatter()
    )
}
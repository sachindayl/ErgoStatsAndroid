package com.technomatesoftware.ergostats.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technomatesoftware.ergostats.config.NumberFormatter

@Composable
fun MetricCircle(percentage: Double?) {
    val numberFormatter = NumberFormatter()
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                progress = percentage?.toFloat() ?: 0.0f,
                strokeWidth = 8.dp,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
            )
        }
        Text(
            numberFormatter.toPercentWithDecimals(percentage?.times(100), 1),
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
    }

}

@Preview
@Composable
fun MetricCirclePreview() {
    MetricCircle(0.23)
}
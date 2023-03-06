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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MetricCard(padding: PaddingValues) {
    Card(
        shape= RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier
            .height(100.dp)
            .width(200.dp).padding(padding)) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            Text("Volume")
            Text("25,000", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MetricCardPreview() {
    MetricCard(PaddingValues(8.dp))
}



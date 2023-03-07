package com.technomatesoftware.ergostats.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AgeUsdTableItem(
    label: String,
    isCorrect: Boolean,
    padding: PaddingValues = PaddingValues(0.dp)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(padding)
            .height(20.dp)
            .width(100.dp)
    ) {
        if (isCorrect) {
            Icon(
                Icons.Rounded.CheckCircle,
                contentDescription = "Check",
                tint = Color.Green,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(label, color = Color.Green, modifier = Modifier.fillMaxHeight())
        } else {

            Icon(
                Icons.Rounded.Clear,
                contentDescription = "Clear",
                tint = Color.Red,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(label, color = Color.Red, modifier = Modifier.fillMaxHeight())
        }

    }
}

@Preview
@Composable
fun AgeUsdTableItemPreview() {
    AgeUsdTableItem("Purchase", false)
}
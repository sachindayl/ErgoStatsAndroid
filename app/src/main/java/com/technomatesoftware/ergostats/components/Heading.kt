package com.technomatesoftware.ergostats.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Heading(paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp), title: String?) {
    title?.let {
        Text(
            it,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview
@Composable
fun HeadingPreview() {
    Heading(paddingValues = PaddingValues(horizontal = 16.dp), title = "Testing")
}
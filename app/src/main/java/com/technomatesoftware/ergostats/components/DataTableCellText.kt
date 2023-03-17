package com.technomatesoftware.ergostats.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technomatesoftware.ergostats.config.EMPTY_STRING

@Composable
fun DataTableCellText(text: String = EMPTY_STRING) {
    Text(
        text = text,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}
package com.technomatesoftware.ergostats.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technomatesoftware.ergostats.R
import com.technomatesoftware.ergostats.config.EMPTY_STRING
import com.technomatesoftware.ergostats.ui.theme.CorrectGreen
import com.technomatesoftware.ergostats.ui.theme.WrongRed

@Composable
fun SummaryItem(title: String, value1: String?, value2: String? = null) {
    Column(Modifier.padding(4.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)
        Text(text = value1 ?: EMPTY_STRING, style = MaterialTheme.typography.titleLarge)
        if (value2 != null) {
            if (value2.toDouble() > 0.0) {
                Row {
                    Icon(
                        painter = painterResource(R.drawable.arrow_up),
                        contentDescription = "Arrow Up",
                        tint = CorrectGreen
                    )
                    Text(
                        text = "$value2%",
                        fontSize = 16.sp,
                        color = CorrectGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            } else if (value2.toDouble() < 0.0) {
                Row {
                    Icon(
                        painter = painterResource(R.drawable.arrow_down),
                        contentDescription = "Arrow Down",
                        tint = WrongRed
                    )
                    Text(
                        text = "$value2%",
                        fontSize = 16.sp,
                        color = WrongRed,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Text(
                    text = "$value2%",
                    fontSize = 16.sp,
                    color = CorrectGreen,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }

    }
}

@Preview
@Composable
fun SummaryItemPreview() {
    SummaryItem(title = "Test", value1 = "$1.35", value2 = "2.34")
}
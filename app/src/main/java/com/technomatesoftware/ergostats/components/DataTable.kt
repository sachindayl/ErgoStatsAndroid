package com.technomatesoftware.ergostats.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel

@Composable
fun DataTable(
    columnCount: Int,
    data: List<SummaryMetricsModel>,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(columnCount)
        ) {
            item(span = {
                // LazyGridItemSpanScope:
                // maxLineSpan
                GridItemSpan(maxLineSpan)
            }) {
                data[1]
            }
            item(span = {
                // LazyGridItemSpanScope:
                // maxLineSpan
                GridItemSpan(maxLineSpan)
            }) {
                data[2]
            }
            // ...
        }


//        LazyRow(
//            modifier = Modifier.padding(16.dp)
//        ) {
//
//            items((0 until columnCount).toList()) { columnIndex ->
//                Column {
//                    (0..data.size).forEach { index ->
//                        Surface(
//                            border = BorderStroke(1.dp, Color.LightGray),
//                            contentColor = Color.Transparent,
//                            modifier = Modifier.width(50.dp)
//                        ) {
//                            if (index == 0) {
//                                headerCellContent(columnIndex)
//                            } else {
//                                cellContent(columnIndex, data[index - 1])
//                            }
//                        }
//                    }
//                }
//            }


//            items((0 until columnCount).toList()) { columnIndex ->
//                Column {
//                    (0..data.size).forEach { index ->
//                        Surface(
//                            border = BorderStroke(1.dp, Color.LightGray),
//                            contentColor = Color.Transparent,
//                            modifier = Modifier.width(50.dp)
//                        ) {
//                            if (index == 0) {
//                                headerCellContent(columnIndex)
//                            } else {
//                                cellContent(columnIndex, data[index - 1])
//                            }
//                        }
//                    }
//                }
//            }
        }

}
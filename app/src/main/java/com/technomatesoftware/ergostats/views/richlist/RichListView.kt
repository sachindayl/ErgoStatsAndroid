package com.technomatesoftware.ergostats.views.richlist

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.technomatesoftware.ergostats.CustomWebView
import com.technomatesoftware.ergostats.domain.models.RichModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModel
import com.technomatesoftware.ergostats.viewmodel.MainViewModelSingleton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichListView(
    padding: PaddingValues,
    mainViewModel: MainViewModel = remember { MainViewModelSingleton.viewModel },
) {
    val context = LocalContext.current
    val richList = mutableListOf(
        RichModel(1, "asdfaf", "25erg"),
        RichModel(2, "tscfaf", "37erg"),
        RichModel(3, "asdgnh", "255erg"),
        RichModel(4, "dsdfaf", "25000erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
        RichModel(5, "uuufaf", "24erg"),
    )
    val listModifier = Modifier
        .padding(padding)

    LaunchedEffect(Unit) {
        mainViewModel.setTitle("Rich List")
        mainViewModel.setHideBottomNavBar(false)
    }

    LazyColumn(modifier = listModifier,) {
        itemsIndexed(richList.toList()) {index, model ->
            ListItem(
                headlineText = { Text(model.address) },
                supportingText = { Text(model.amount)},
                leadingContent = {
                    Text("${index + 1}")
                },
                modifier = Modifier.clickable {
                    val intent = Intent(context, CustomWebView::class.java)
                    intent.putExtra("url", "https://www.google.com")
                    intent.putExtra("address", model.address)
                    context.startActivity(intent)
                }
            )
            Divider()
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewSearchView() {
    Scaffold { padding ->
        RichListView(padding = padding)
    }
}
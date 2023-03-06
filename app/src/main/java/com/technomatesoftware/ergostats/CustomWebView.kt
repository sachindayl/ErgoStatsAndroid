package com.technomatesoftware.ergostats

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.technomatesoftware.ergostats.ui.theme.ErgoStatsTheme


class CustomWebView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra("url")
        val address = intent.getStringExtra("address")

        setContent {
            CustomWebViewContent(url, address)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomWebViewContent(url: String?, address: String?) {
    val context = LocalContext.current
    ErgoStatsTheme {
        Scaffold(topBar = {

            TopAppBar(
                title = { address?.let { Text(text = it) } },
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                MainActivity::class.java
                            )
                        )
                        return@IconButton
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }) { padding ->
            WebViewScreen(padding, url)
        }

    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(padding: PaddingValues, url: String?) {
    val webViewState = remember {
        mutableStateOf(WebViewState(false))
    }

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        WebView(
            modifier = Modifier.fillMaxSize(),
            onWebViewStateChange = { webViewState.value = it },
            onLoaded = {
                it.settings.javaScriptEnabled = true
                it.loadUrl(url ?: "")
            }
        )

        if (webViewState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

data class WebViewState(val isLoading: Boolean)

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
    modifier: Modifier = Modifier,
    onWebViewStateChange: (WebViewState) -> Unit = {},
    onLoaded: (WebView) -> Unit = {},
) {
    val webViewRef = remember {
        mutableStateOf<WebView?>(null)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                webViewRef.value = this
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        onWebViewStateChange(WebViewState(false))
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        onWebViewStateChange(WebViewState(true))
                    }
                }

                onLoaded(this)
            }
        },
        update = {
            webViewRef.value = it
        }
    )
}

@Preview(showBackground = true)
@Composable
fun WebViewPreview() {
    CustomWebView()
}

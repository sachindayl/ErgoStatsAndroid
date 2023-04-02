package com.technomatesoftware.ergostats.config

import android.content.Context
import android.net.Uri
import androidx.appcompat.R
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat

class AppBrowser {
    fun openBrowserTab(context: Context, url: String) {
        val packageName = "com.android.chrome"
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
        builder.setInstantAppsEnabled(true)
        val params = CustomTabColorSchemeParams.Builder()
            .setNavigationBarColor(
                ContextCompat.getColor(
                    context,
                    R.color.primary_material_dark
                )
            )
            .setToolbarColor(
                ContextCompat.getColor(
                    context,
                    R.color.primary_material_dark
                )
            )
            .setSecondaryToolbarColor(
                ContextCompat.getColor(
                    context,
                    R.color.primary_material_dark
                )
            )
            .build()
        builder.setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, params)
        val customBuilder = builder.build()
        customBuilder.intent.setPackage(packageName)
        customBuilder.launchUrl(context, Uri.parse(url))
    }
}
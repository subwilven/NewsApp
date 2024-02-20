package com.example.newsapp.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.text.format.DateUtils
import androidx.core.content.ContextCompat
import java.util.Date

fun Date.convertToAgoTime(): String {
    return DateUtils.getRelativeTimeSpanString(
        time,
        System.currentTimeMillis(), 0
    ).toString()
}

@Suppress("DEPRECATION")
fun isCurrentlyConnected(context: Context): Boolean {
    return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.run {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> activeNetwork?.let(::getNetworkCapabilities)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false

            else -> activeNetworkInfo?.isConnected ?: false
        }
    } ?: true
}

fun launchWebView(context: Context, url: String?) {
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    ContextCompat.startActivity(context, webIntent, null)
}

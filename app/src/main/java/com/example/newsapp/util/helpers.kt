package com.example.newsapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.format.DateUtils
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import java.util.*

fun Date.convertToAgoTime():String{
    return DateUtils.getRelativeTimeSpanString(
        time ?: 0,
        System.currentTimeMillis(), 0
    ).toString()
}

//todo check how to handle this
fun getFavoriteIcon(isFavorite: Boolean) = if (isFavorite) Icons.Filled.Favorite
    else Icons.Filled.FavoriteBorder


@Suppress("DEPRECATION")
fun isCurrentlyConnected(context: Context) :Boolean=  with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
    when (this) {
        null -> false
        else -> when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> activeNetwork?.let(::getNetworkCapabilities)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
            else -> activeNetworkInfo?.isConnected ?: false
        }
    }
}
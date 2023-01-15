package com.example.newsapp.util

import android.text.format.DateUtils
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.res.painterResource
import com.example.newsapp.R
import com.example.newsapp.model.articles.ArticleUi
import java.text.SimpleDateFormat
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
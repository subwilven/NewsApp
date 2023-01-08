package com.example.newsapp.util

import android.text.format.DateUtils
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
fun getFavoriteIcon(isFavorite: Boolean) = if (isFavorite) R.drawable.ic_favorite_24
    else R.drawable.ic_favorite_border_24
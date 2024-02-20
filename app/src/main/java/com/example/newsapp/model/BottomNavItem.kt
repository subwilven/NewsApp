package com.example.newsapp.model

import androidx.annotation.StringRes
import com.example.newsapp.R
import com.example.newsapp.util.Constants.Destinations.ARTICLES
import com.example.newsapp.util.Constants.Destinations.FAVORITES


enum class BottomNavItem(
    @StringRes val titleRes: Int,
    val icon: Int,
    val route: String,
) {

    Articles(
        R.string.home,
        R.drawable.ic_home_24,
        ARTICLES,
    ),

    MyFavorites(
        R.string.favorites,
        R.drawable.ic_favorite_24,
        FAVORITES,
    )
}

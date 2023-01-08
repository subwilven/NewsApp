package com.example.newsapp.model

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.example.newsapp.R
import com.example.newsapp.navigation.Destination


sealed class BottomNavItem(@StringRes var titleRes: Int, var icon: Int, var destination: Destination) {

    object Articles : BottomNavItem(
        R.string.home,
        R.drawable.ic_home_24,
        Destination.ArticlesScreen
    )

    object MyFavorites : BottomNavItem(
        R.string.favorites,
        R.drawable.ic_favorite_24,
        Destination.FavoritesScreen
    )
}
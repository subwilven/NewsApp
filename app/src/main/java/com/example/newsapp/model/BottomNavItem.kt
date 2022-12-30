package com.example.newsapp.model

import com.example.newsapp.navigation.Destination
import com.example.newsapp.util.NewsAppScreens


sealed class BottomNavItem(var title: String, var icon: Int, var destination: Destination) {

    object Articles : BottomNavItem(
        "Articles",
        android.R.drawable.ic_menu_help,
        Destination.ArticlesScreen
    )

    object MyFavorites : BottomNavItem(
        "My Favorites",
        android.R.drawable.ic_search_category_default,
        Destination.FavoritesScreen
    )
}
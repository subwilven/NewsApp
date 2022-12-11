package com.example.newsapp.model

import com.example.newsapp.util.NewsAppScreens


sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {

    object Articles : BottomNavItem(
        "Articles",
        android.R.drawable.ic_menu_help,
        NewsAppScreens.ArticlesScreen.route
    )

    object MyFavorites : BottomNavItem(
        "My Favorites",
        android.R.drawable.ic_search_category_default,
        NewsAppScreens.FavoritesScreen.route
    )
}
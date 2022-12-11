package com.example.newsapp.util

sealed class NewsAppScreens(val route: String) {
    object ArticlesScreen :NewsAppScreens("articles")
    object FavoritesScreen :NewsAppScreens("favorites")
    object ArticleDetailsScreen :NewsAppScreens("article-details")
}

const val ARG_ARTICLE_ID = "articleId"
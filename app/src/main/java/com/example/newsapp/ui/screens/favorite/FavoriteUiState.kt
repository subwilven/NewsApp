package com.example.newsapp.ui.screens.favorite

import com.example.newsapp.model.articles.Article

data class FavoriteUiState(
    var isLoading: Boolean = true,
    var favoriteArticles: List<Article> = emptyList(),
)

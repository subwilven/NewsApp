package com.example.newsapp.ui.screens.favorite

import com.example.newsapp.model.articles.Article

data class FavoriteUiState(
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var favoriteArticles: List<Article> = emptyList(),
)

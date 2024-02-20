package com.example.newsapp.ui.screens.favorite

import com.example.newsapp.model.articles.Article

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val favoriteArticles: List<Article> = emptyList(),
)

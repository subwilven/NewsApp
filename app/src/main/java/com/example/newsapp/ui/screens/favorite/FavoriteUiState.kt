package com.example.newsapp.ui.screens.favorite

import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.model.providers.ProviderUi

data class FavoriteUiState(
    var isLoading: Boolean = true,
    var favoriteArticles: List<ArticleUi> = emptyList(),
)

package com.example.newsapp.ui.screens.articles

import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.providers.Provider

sealed class ArticlesActions {
    data class SearchArticlesAction(val searchInput: String?): ArticlesActions()
    data class FilterByProvidersAction(val selectedProviders: List<Provider>): ArticlesActions()
    data class AddToFavoriteAction(val article: Article): ArticlesActions()
}
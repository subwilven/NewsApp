package com.example.newsapp.ui.screens.articles

import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.model.providers.ProviderUi

sealed class ArticlesActions {
    data class SearchArticlesAction(val searchInput: String?): ArticlesActions()
    data class FilterByProvidersAction(val selectedProviders: List<ProviderUi>): ArticlesActions()
    data class AddToFavoriteAction(val article: ArticleUi): ArticlesActions()
}
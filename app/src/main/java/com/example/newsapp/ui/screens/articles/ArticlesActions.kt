package com.example.newsapp.ui.screens.articles

import com.example.newsapp.model.articles.ArticleUi

sealed class ArticlesActions {
    data class SearchArticlesAction(val searchInput: String?): ArticlesActions()
    data class AddToFavoriteAction(val article: ArticleUi): ArticlesActions()
}
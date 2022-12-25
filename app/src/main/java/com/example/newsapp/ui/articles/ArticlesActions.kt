package com.example.newsapp.ui.articles

import com.example.newsapp.model.articles.ArticleUi

sealed class ArticlesActions {
    data class SearchArticlesAction(val query: String?): ArticlesActions()
    data class AddToFavoriteAction(val article: ArticleUi): ArticlesActions()
}
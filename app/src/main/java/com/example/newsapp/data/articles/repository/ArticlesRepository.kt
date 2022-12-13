package com.example.newsapp.data.articles.repository

import androidx.paging.PagingData
import com.example.newsapp.model.articles.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    fun getArticlesStream(query: String?): Flow<PagingData<Article>>
    suspend fun getArticleById(articleId: Int): Article
    fun getFavoritesArticles(): Flow<List<Article>>
    suspend fun changeFavoriteState(articleId:Int,isFavorite :Boolean)
}
package com.example.newsapp.data.articles.repository

import androidx.paging.PagingData
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.providers.Provider
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    fun getArticlesStream(filterData: FilterData): Flow<PagingData<Article>>
    fun getArticleById(articleId: Int): Flow<Article>
    fun getFavoritesArticles(): Flow<List<Article>>
    suspend fun changeFavoriteState(articleId:Int,isFavorite :Boolean)
    suspend fun  getProviders() : List<Provider>
}
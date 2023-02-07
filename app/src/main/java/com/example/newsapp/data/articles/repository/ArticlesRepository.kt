package com.example.newsapp.data.articles.repository

import androidx.paging.PagingData
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    fun getArticlesStream(filterData: FilterData): Flow<PagingData<ArticleEntity>>
    fun getArticleById(articleId: Int): Flow<ArticleEntity>
    fun getFavoritesArticles(): Flow<List<ArticleEntity>>
    suspend fun changeFavoriteState(articleId:Int,isFavorite :Boolean)

}
package com.example.newsapp.data.articles.repository

import androidx.paging.PagingData
import com.example.newsapp.model.articles.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    fun getArticlesStream(query: String?): Flow<PagingData<Article>>
    fun getArticleById(articleId: Int): Flow<Article>
}
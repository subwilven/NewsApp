package com.example.newsapp.data.articles.data_source.local

import androidx.paging.PagingSource
import com.example.newsapp.model.articles.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesLocalDataSource {
   fun fetchArticles(query :String?) : PagingSource<Int,Article>
   suspend fun insertAllArticles(articles : List<Article>)
   suspend fun insertAllArticlesAndDeleteOld(articles : List<Article>)
   suspend fun deleteAllArticles()
   fun getArticleById(articleId :Int): Flow<Article>
}
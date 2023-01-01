package com.example.newsapp.data.articles.data_source.local

import androidx.paging.PagingSource
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.providers.Provider
import kotlinx.coroutines.flow.Flow

interface ArticlesLocalDataSource {
   fun fetchArticles(searchInput :String?) : PagingSource<Int,Article>
   suspend fun insertAllArticles(articles : List<Article>)
   suspend fun insertAllArticlesAndDeleteOld(articles : List<Article>)
   suspend fun deleteAllArticles()
   suspend fun getArticleById(articleId :Int): Article
   fun getFavoritesArticles() : Flow<List<Article>>
   suspend fun changeFavoriteState(articleId:Int,isFavorite :Boolean)
   fun getProviders() : Flow<List<Provider>>
   suspend fun getProviderCounts() : Int
   suspend fun insertAllSources(sources : List<Provider>)
}